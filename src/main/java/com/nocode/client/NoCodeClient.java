/**
 * 
 */
package com.nocode.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.nocode.constants.ConfigProperty;
import com.nocode.constants.ResourceFile;
import com.nocode.exception.HttpException;
import com.nocode.model.ScenarioSteps;
import com.nocode.model.Steps;
import com.nocode.model.TestData;
import com.nocode.model.TestDataProvider;
import com.nocode.utils.ILogger;
import com.nocode.utils.JavaUtil;
import com.nocode.utils.PropertyUtil;

/**
 * The Class NoCodeClient.
 *
 * @author Pavan.DV
 * @since 2.0.0
 */
public class NoCodeClient implements ILogger {

	/**
	 * Gets the execution file.
	 *
	 * @return the execution file
	 */
	public ScenarioSteps[] getExecutionFile() {
		Object executionFile = PropertyUtil.get(ConfigProperty.EXECUTION_FILE);
		ScenarioSteps[] scenarioSteps = getStepsFromJson(executionFile.toString());
		return null != scenarioSteps ? scenarioSteps : new ScenarioSteps[0];
	}

	/**
	 * Gets the steps from json.
	 *
	 * @param jsonFile the json file
	 * @return the steps from json
	 */
	private ScenarioSteps[] getStepsFromJson(String jsonFile) {
		String filePath = TestNGRunner.class.getClassLoader().getResource(ResourceFile.TEST.getValue() + "/" + jsonFile)
				.getPath();
		ScenarioSteps[] scenarioSteps = (ScenarioSteps[]) JavaUtil.getClassFromJsonArray(filePath,
				ScenarioSteps[].class);
		ScenarioSteps[] scenarioStepsWithExecuteFlagY = Arrays.stream(scenarioSteps)
				.filter(json -> json.getExecute().equalsIgnoreCase("y")).toArray(ScenarioSteps[]::new);
		return getScenarioStepsWithTestData(scenarioStepsWithExecuteFlagY);
	}

	/**
	 * Gets the scenario steps with test data.
	 *
	 * @param scenarioStepsWithExecuteFlagY the scenario steps with execute flag Y
	 * @return the scenario steps with test data
	 */
	private ScenarioSteps[] getScenarioStepsWithTestData(ScenarioSteps[] scenarioStepsWithExecuteFlagY) {
		List<ScenarioSteps> scenarioStepsWithDataProvider = new ArrayList<>();
		for (ScenarioSteps scenario : scenarioStepsWithExecuteFlagY) {
			if (null != scenario.getRequest() && null != scenario.getValidate())
				createScenarioSteps(scenario, scenarioStepsWithDataProvider);
			else {
				scenarioStepsWithDataProvider.add(scenario.copy());
				createScenarioStepsWithMultiSteps(scenario, scenarioStepsWithDataProvider);
			}
		}
		return scenarioStepsWithDataProvider.stream().toArray(ScenarioSteps[]::new);
	}

	/**
	 * Create scenario steps using steps array.
	 *
	 * @param scenarioSteps the scenario steps
	 * @param scenarioStepsWithDataProvider the scenario steps with data provider
	 */
	private void createScenarioStepsWithMultiSteps(ScenarioSteps scenarioSteps,
			List<ScenarioSteps> scenarioStepsWithDataProvider) {
		for (Steps mSteps : scenarioSteps.getSteps()) {
			String testdata = (String) mSteps.getRequest().getTestdata();
			if (testdata != null && !testdata.isEmpty() && !testdata.equals("null")) {
				if (!testdata.endsWith(".json"))
					throw new JSONException(testdata + " is not a valid json file");
				String filePath = TestNGRunner.class.getClassLoader()
						.getResource(ResourceFile.TEST_DATA.getValue() + "/" + mSteps.getRequest().getTestdata())
						.getPath();
				TestData testData = (TestData) JavaUtil.getClassFromJsonObject(filePath, TestData.class);
				if (null != testData.getDataprovider() && !testData.getDataprovider().isEmpty()) {
					List<TestDataProvider> testDataProviderList = testData.getDataprovider();
					for (int i = 0; i < testDataProviderList.size(); i++)
						scenarioStepsWithDataProvider.add(
								createScenarioStepsWithTestData(scenarioSteps, testData, testDataProviderList.get(i)));
				} else if (null != testData.getBody())
					createScenarioStepsWithTestDataBodyMultiSteps(scenarioStepsWithDataProvider.get(0), testData,
							mSteps);
			}
//			else
//				scenarioStepsWithDataProvider.add(scenarioSteps);
		}
	}

	/**
	 * Create scenario steps.
	 *
	 * @param scenario the scenario
	 * @param scenarioStepsWithDataProvider the scenario steps with data provider
	 */
	private void createScenarioSteps(ScenarioSteps scenario, List<ScenarioSteps> scenarioStepsWithDataProvider) {
		String testdata = (String) scenario.getRequest().getTestdata();
		if (testdata != null && !testdata.isEmpty() && !testdata.equals("null")) {
			if (!testdata.endsWith(".json"))
				throw new JSONException(testdata + " is not a valid json file");
			String filePath = TestNGRunner.class.getClassLoader()
					.getResource(ResourceFile.TEST_DATA.getValue() + "/" + scenario.getRequest().getTestdata())
					.getPath();
			TestData testData = (TestData) JavaUtil.getClassFromJsonObject(filePath, TestData.class);
			if (null != testData.getDataprovider() && !testData.getDataprovider().isEmpty()) {
				List<TestDataProvider> testDataProviderList = testData.getDataprovider();
				for (int i = 0; i < testDataProviderList.size(); i++)
					scenarioStepsWithDataProvider
							.add(createScenarioStepsWithTestData(scenario, testData, testDataProviderList.get(i)));
			} else if (null != testData.getBody())
				scenarioStepsWithDataProvider.add(createScenarioStepsWithTestDataBody(scenario, testData));
		} else
			scenarioStepsWithDataProvider.add(scenario);
	}

	/**
	 * Creates the scenario steps with test data.
	 *
	 * @param scenario the scenario
	 * @param testData the test data
	 * @param testDataProvider the test data provider
	 * @return the scenario steps
	 */
	private ScenarioSteps createScenarioStepsWithTestData(ScenarioSteps scenario, TestData testData,
			TestDataProvider testDataProvider) {
		ScenarioSteps copyOfScenarioSteps = scenario.copy();
		String url = copyOfScenarioSteps.getRequest().getUrl();
		Iterator<String> unknownParamsIterator = JavaUtil
				.retrieveUnknownParams(copyOfScenarioSteps.getRequest().getUrl()).iterator();
		while (unknownParamsIterator.hasNext()) {
			String param = unknownParamsIterator.next();
			url = url.replace("{" + param + "}", testDataProvider.getUrl().get(param).toString());
		}
		copyOfScenarioSteps.getRequest().setUrl(url);
		if (null != testDataProvider.getBody())
			setRequestBodyWithTestData(copyOfScenarioSteps, testData, testDataProvider);
		else
			copyOfScenarioSteps.getRequest().setTestdata(null);
		if (null != testDataProvider.getResbody() && null != copyOfScenarioSteps.getValidate().getResbody())
			copyOfScenarioSteps.getValidate().setResbody(testDataProvider.getResbody());
		if (testDataProvider.getStatuscode() != 0)
			copyOfScenarioSteps.getValidate().setStatuscode(testDataProvider.getStatuscode());
		return copyOfScenarioSteps;
	}

	/**
	 * Creates the scenario steps with test data body.
	 *
	 * @param scenario the scenario
	 * @param testData the test data
	 * @return the scenario steps
	 */
	private ScenarioSteps createScenarioStepsWithTestDataBody(ScenarioSteps scenario, TestData testData) {
		ScenarioSteps copyOfScenarioSteps = scenario.copy();
		Map<String, Object> body = JavaUtil.getMapFromObject(testData.getBody());
		copyOfScenarioSteps.getRequest().setTestdata(JavaUtil.getJsonFromMap(body));
		return copyOfScenarioSteps;
	}

	/**
	 * Creates the scenario steps with test data body multi steps.
	 *
	 * @param copyOfScenarioSteps the copy of scenario steps
	 * @param testData the test data
	 * @param mSteps the m steps
	 * @return the scenario steps
	 */
	private ScenarioSteps createScenarioStepsWithTestDataBodyMultiSteps(ScenarioSteps copyOfScenarioSteps,
			TestData testData, Steps mSteps) {
		Map<String, Object> body = JavaUtil.getMapFromObject(testData.getBody());
		for (Steps copyOfSteps : copyOfScenarioSteps.getSteps()) {
			if (copyOfSteps.getStep().equalsIgnoreCase(mSteps.getStep()))
				copyOfSteps.getRequest().setTestdata(JavaUtil.getJsonFromMap(body));
		}
		return copyOfScenarioSteps;
	}

	/**
	 * Sets the request body with test data.
	 *
	 * @param copyOfScenarioSteps the copy of scenario steps
	 * @param testData the test data
	 * @param testDataProvider the test data provider
	 */
	private void setRequestBodyWithTestData(ScenarioSteps copyOfScenarioSteps, TestData testData,
			TestDataProvider testDataProvider) {
		Map<String, Object> refBody = JavaUtil.getMapFromObject(testData.getBody());
		Map<String, Object> dpBody = JavaUtil.getMapFromObject(testDataProvider.getBody());
		copyOfScenarioSteps.getRequest().setTestdata(JavaUtil.getJsonFromMap(JavaUtil.deepMergeMaps(refBody, dpBody)));
	}

	/**
	 * Gets the execution scenarios.
	 *
	 * @return the execution scenarios
	 */
	public ScenarioSteps[] getExecutionScenarios() {
		Object executionFile = null;
		try {
			PropertyUtil.loadProperties(ResourceFile.CONFIG_FILE);
			try {
				executionFile = PropertyUtil.get(ConfigProperty.EXECUTION_FILE);
				String filePath = TestNGRunner.class.getClassLoader()
						.getResource(ResourceFile.TEST.getValue() + "/" + executionFile.toString()).getPath();
				return (ScenarioSteps[]) JavaUtil.getClassFromJsonArray(filePath, ScenarioSteps[].class);
			} catch (NullPointerException e) {
				throw new HttpException("execution_file not found in config.properties");
			}
		} catch (NullPointerException e) {
			throw new HttpException("config.properties not found in src/main/resources");
		}
	}
}

package com.nocode.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.TestNG;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nocode.constants.ConfigProperty;
import com.nocode.constants.HttpMethod;
import com.nocode.constants.ResourceFile;
import com.nocode.listners.NoCodeListener;
import com.nocode.listners.ReportListener;
import com.nocode.model.ScenarioSteps;
import com.nocode.model.TestData;
import com.nocode.model.TestDataProvider;
import com.nocode.response.HttpResponse;
import com.nocode.utils.JavaUtil;
import com.nocode.utils.PropertyUtil;
import com.nocode.utils.ResponseValidator;

/**
 * 
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
@Listeners(ReportListener.class)
public class TestNGRunner implements ITest {

	private ThreadLocal<String> testName = new ThreadLocal<>();

	public static TestNGRunner getInstance() {
		return new TestNGRunner();
	}

	public void runTestClass() {
		TestNG suite = new TestNG();
		suite.addListener(new NoCodeListener());
		suite.setVerbose(2);
		suite.setOutputDirectory("test-output");
		suite.setTestClasses(new Class[] { TestNGRunner.class });
		suite.run();
	}

	public ScenarioSteps[] getExecutionFile() {
		Object executionFile = PropertyUtil.get(ConfigProperty.EXECUTION_FILE);
		ScenarioSteps[] scenarioSteps = getStepsFromJson(executionFile.toString());
		return null != scenarioSteps ? scenarioSteps : new ScenarioSteps[0];
	}

	private ScenarioSteps[] getStepsFromJson(String jsonFile) {
		String filePath = TestNGRunner.class.getClassLoader().getResource(ResourceFile.TEST.getValue() + "/" + jsonFile)
				.getPath();
		ScenarioSteps[] scenarioSteps = (ScenarioSteps[]) JavaUtil.getClassFromJsonArray(filePath,
				ScenarioSteps[].class);
		ScenarioSteps[] scenarioStepsWithExecuteFlagY = Arrays.stream(scenarioSteps)
				.filter(json -> json.getExecute().equalsIgnoreCase("y")).toArray(ScenarioSteps[]::new);
		return getScenarioStepsWithTestData(scenarioStepsWithExecuteFlagY);
	}

	private ScenarioSteps[] getScenarioStepsWithTestData(ScenarioSteps[] scenarioStepsWithExecuteFlagY) {
		List<ScenarioSteps> scenarioStepsWithDataProvider = new ArrayList<>();
		for (ScenarioSteps scenario : scenarioStepsWithExecuteFlagY) {
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
		return scenarioStepsWithDataProvider.stream().toArray(ScenarioSteps[]::new);
	}

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
		if(testDataProvider.getStatuscode()!=0)
			copyOfScenarioSteps.getValidate().setStatuscode(testDataProvider.getStatuscode());
		return copyOfScenarioSteps;
	}

	private ScenarioSteps createScenarioStepsWithTestDataBody(ScenarioSteps scenario, TestData testData) {
		ScenarioSteps copyOfScenarioSteps = scenario.copy();
		Map<String, Object> body = JavaUtil.getMapFromObject(testData.getBody());
		copyOfScenarioSteps.getRequest().setTestdata(JavaUtil.getJsonFromMap(body));
		return copyOfScenarioSteps;
	}

	private void setRequestBodyWithTestData(ScenarioSteps copyOfScenarioSteps, TestData testData,
			TestDataProvider testDataProvider) {
		Map<String, Object> refBody = JavaUtil.getMapFromObject(testData.getBody());
		Map<String, Object> dpBody = JavaUtil.getMapFromObject(testDataProvider.getBody());
		copyOfScenarioSteps.getRequest().setTestdata(JavaUtil.getJsonFromMap(deepMergeMaps(refBody, dpBody)));
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> deepMergeMaps(Map<String, Object> originalMap,
			Map<String, Object> mapToUpdateOriginal) {
		for (String key : mapToUpdateOriginal.keySet()) {
			if (mapToUpdateOriginal.get(key) instanceof Map && originalMap.get(key) instanceof Map) {
				Map<String, Object> originalChild = (Map<String, Object>) originalMap.get(key);
				Map<String, Object> newChild = (Map<String, Object>) mapToUpdateOriginal.get(key);
				originalMap.put(key, deepMergeMaps(originalChild, newChild));
			} else if (mapToUpdateOriginal.get(key) instanceof List && originalMap.get(key) instanceof List) {
				List<Object> originalChild = (List<Object>) originalMap.get(key);
				List<?> newChild = (List<?>) mapToUpdateOriginal.get(key);
				for (Object each : newChild) {
					if (!originalChild.contains(each)) {
						originalChild.add(each);
					}
				}
			} else {
				originalMap.put(key, mapToUpdateOriginal.get(key));
			}
		}
		return originalMap;
	}

	@DataProvider
	private Object[][] dataProvider() {
		ScenarioSteps[] testScenarios = getExecutionFile();
		int numOfScenrios = testScenarios.length;
		Object[][] data = new Object[numOfScenrios][2];
		for (int j = 0; j < numOfScenrios; j++) {
			for (int i = 0; i < 1; i++) {
				data[j][0] = testScenarios[j].getScenario();
				data[j][1] = testScenarios[j];
			}
		}
		return data;
	}

	@Test(dataProvider = "dataProvider")
	private void run(String description, ScenarioSteps scenario) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addMethod(HttpMethod.valueOf(scenario.getRequest().getMethod().toUpperCase()))
				.addBaseUrl(scenario.getRequest().getUrl()).addHeader(scenario.getRequest().getHeaders())
				.addQueryParams(scenario.getRequest().getQueryparam()).addBody(scenario.getRequest().getTestdata());
		HttpResponse httpResponse = Request.execute(httpRequest);
		new ResponseValidator(scenario.getValidate(), httpResponse);
	}

	@Override
	public String getTestName() {
		return testName.get();
	}

	@BeforeMethod
	public void beforeMethod(Object[] testData, ITestContext ctx) {
		testName.set(testData[0].toString());
		ctx.setAttribute("testName", testName.get());
	}

	public ScenarioSteps[] getExecutionScenarios() {
		PropertyUtil.loadProperties(ResourceFile.CONFIG_FILE);
		Object executionFile = PropertyUtil.get(ConfigProperty.EXECUTION_FILE);
		String filePath = TestNGRunner.class.getClassLoader().getResource(ResourceFile.TEST.getValue() + "/" + executionFile.toString())
				.getPath();
		return (ScenarioSteps[]) JavaUtil.getClassFromJsonArray(filePath,
				ScenarioSteps[].class);
	}
}

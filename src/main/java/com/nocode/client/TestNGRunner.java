package com.nocode.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nocode.constants.HttpMethod;
import com.nocode.model.ScenarioSteps;
import com.nocode.model.TestData;
import com.nocode.model.TestDataProvider;
import com.nocode.response.HttpResponse;
import com.nocode.utils.JavaUtil;
import com.nocode.utils.PropertyUtil;
import com.nocode.utils.ResponseValidator;

public class TestNGRunner {

	public static TestNGRunner getInstance() {
		return new TestNGRunner();
	}

	public ScenarioSteps[] getExecutionFile() {
		InputStream inputstream = TestNGRunner.class.getClassLoader().getResourceAsStream("config.properties");
		Object executionFile = PropertyUtil.loadProperties(inputstream).get("execution_file");
		ScenarioSteps[] scenarioSteps = getStepsFromJson(executionFile.toString());
		return null != scenarioSteps ? scenarioSteps : new ScenarioSteps[0];
	}

	private ScenarioSteps[] getStepsFromJson(String jsonFile) {
		ScenarioSteps[] scenarioSteps = (ScenarioSteps[]) JavaUtil
				.getClassFromJsonArray("./src/main/resources/test/" + jsonFile, ScenarioSteps[].class);
		ScenarioSteps[] scenarioStepsWithExecuteFlagY = Arrays.stream(scenarioSteps)
				.filter(json -> json.getExecute().equalsIgnoreCase("y")).toArray(ScenarioSteps[]::new);
		return getScenarioStepsWithTestData(scenarioStepsWithExecuteFlagY);
	}


	private ScenarioSteps[] getScenarioStepsWithTestData(ScenarioSteps[] scenarioStepsWithExecuteFlagY) {
		List<ScenarioSteps> scenarioStepsWithDataProvider = new ArrayList<>();
		for (ScenarioSteps scenario : scenarioStepsWithExecuteFlagY) {
			String testdata = scenario.getRequest().getTestdata();
			if (testdata != null && !testdata.isEmpty() && !testdata.equals("null")) {
				if (!testdata.endsWith(".json"))
					throw new JSONException(testdata + " is not a valid json file");
				TestData testData = (TestData) JavaUtil.getClassFromJsonObject(
						"./src/main/resources/testdata/" + scenario.getRequest().getTestdata(), TestData.class);
				if (!testData.getDataprovider().isEmpty()) {
					List<TestDataProvider> testDataProviderList = testData.getDataprovider();
					for (int i = 0; i < testDataProviderList.size(); i++) {
						scenarioStepsWithDataProvider
								.add(createScenarioStepsWithTestData(scenario, testData, testDataProviderList.get(i)));
					}
				}
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
		if (null != testDataProvider.getResbody() && null!=copyOfScenarioSteps.getValidate().getResbody())
			copyOfScenarioSteps.getValidate().setResbody(testDataProvider.getResbody());
		return copyOfScenarioSteps;
	}

	private void setRequestBodyWithTestData(ScenarioSteps copyOfScenarioSteps, TestData testData,
			TestDataProvider testDataProvider) {
		Map<String, Object> modifiedBody = new HashMap<>();
		Map<String, Object> refBody = JavaUtil.getMapFromJson(testData.getBody());
		Map<String, Object> dpBody = JavaUtil.getMapFromJson(testDataProvider.getBody());
		modifiedBody.putAll(refBody);
		for (Entry<String, Object> refEntry : refBody.entrySet()) {
			for (Entry<String, Object> dpEntry : dpBody.entrySet()) {
				if (refEntry.getKey().equalsIgnoreCase(dpEntry.getKey())) {
					modifiedBody.put(refEntry.getKey(), dpEntry.getValue());
				}
			}
		}
		copyOfScenarioSteps.getRequest().setTestdata(JavaUtil.getJsonFromMap(modifiedBody).toString());
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
	public void run(String description, ScenarioSteps scenario) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addMethod(HttpMethod.valueOf(scenario.getRequest().getMethod().toUpperCase()))
				.addBaseUrl(scenario.getRequest().getUrl()).addHeader(scenario.getRequest().getHeaders())
				.addQueryParams(scenario.getRequest().getQueryparam()).addBody(scenario.getRequest().getTestdata());
		HttpResponse httpResponse = Request.execute(httpRequest);
		new ResponseValidator(scenario.getValidate(), httpResponse);
	}

}

package com.nocode.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nocode.constants.HttpMethod;
import com.nocode.model.ScenarioSteps;
import com.nocode.model.TestData;
import com.nocode.model.TestDataProvider;
import com.nocode.response.HttpResponse;
import com.nocode.utils.ILogger;
import com.nocode.utils.JavaUtil;
import com.nocode.utils.PropertyUtil;
import com.nocode.utils.ResponseValidator;

public class TestNGRunner implements ILogger {

	public static TestNGRunner getInstance() {
		return new TestNGRunner();
	}

	public ScenarioSteps[] getExecutionFile() {
		InputStream inputstream = TestNGRunner.class.getClassLoader().getResourceAsStream("application.properties");
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

	private Object getActualBody(ScenarioSteps scenario) {
		String testdata = scenario.getRequest().getTestdata();
		Object jsonBody = null;
		if (testdata != null && !testdata.isEmpty() && !testdata.equals("null") && !testdata.endsWith(".json")) {
			testdata = testdata.replace("{", "").replace("}", "").trim();
			Map<String, Object> reconstructedMap = Arrays.stream(testdata.split(","))
					.collect(Collectors.toMap(t -> (t.split("=")[0]).trim(), t -> t.split("=")[1]));
			jsonBody = JavaUtil.getJsonFromMap(reconstructedMap);
		}
		return jsonBody;
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
					List<TestDataProvider> testDataProvider = testData.getDataprovider();
					for (int i = 0; i < testDataProvider.size(); i++) {
						ScenarioSteps copyOfScenarioSteps = scenario.copy();
						String url = copyOfScenarioSteps.getRequest().getUrl();
						Iterator<String> unknownParamsIterator = JavaUtil
								.retrieveUnknownParams(copyOfScenarioSteps.getRequest().getUrl()).iterator();
						while (unknownParamsIterator.hasNext()) {
							String param = unknownParamsIterator.next();
							url = url.replace("{" + param + "}",
									testDataProvider.get(i).getUrl().get(param).toString());
						}
						copyOfScenarioSteps.getRequest().setUrl(url);
						if (null != testDataProvider.get(i).getBody())
							copyOfScenarioSteps.getRequest().setTestdata(testDataProvider.get(i).getBody().toString());
						if (null != testDataProvider.get(i).getResbody())
							copyOfScenarioSteps.getValidate().setResbody(testDataProvider.get(i).getResbody());
						scenarioStepsWithDataProvider.add(copyOfScenarioSteps);
					}
				}
			} else
				scenarioStepsWithDataProvider.add(scenario);

		}
		return scenarioStepsWithDataProvider.stream().toArray(ScenarioSteps[]::new);
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
				.addQueryParams(scenario.getRequest().getQueryparam()).addBody(getActualBody(scenario));
		HttpResponse httpResponse = Request.execute(httpRequest);
		new ResponseValidator(scenario.getValidate(), httpResponse);
	}

}

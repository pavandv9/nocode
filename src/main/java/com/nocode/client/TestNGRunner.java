package com.nocode.client;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.TestNG;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nocode.constants.HttpMethod;
import com.nocode.listners.NoCodeListener;
import com.nocode.listners.ReportListener;
import com.nocode.model.ScenarioSteps;
import com.nocode.model.Steps;
import com.nocode.response.HttpResponse;
import com.nocode.utils.ResponseValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class TestNGRunner.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
@Listeners(ReportListener.class)
public class TestNGRunner implements ITest {

	/** The test name. */
	private ThreadLocal<String> testName = new ThreadLocal<>();
	
	/** The m step client. */
	private MultiStepClient mStepClient = new MultiStepClient();

	/**
	 * Gets the single instance of TestNGRunner.
	 *
	 * @return single instance of TestNGRunner
	 */
	public static TestNGRunner getInstance() {
		return new TestNGRunner();
	}

	/**
	 * Run test class.
	 */
	public void runTestClass() {
		TestNG suite = new TestNG();
		suite.addListener(new NoCodeListener());
		suite.setVerbose(2);
		suite.setOutputDirectory("test-output");
		suite.setTestClasses(new Class[] { TestNGRunner.class });
		suite.run();
	}

	/**
	 * Data provider.
	 *
	 * @return the object[][]
	 */
	@DataProvider
	private Object[][] dataProvider() {
		ScenarioSteps[] testScenarios = new NoCodeClient().getExecutionFile();
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

	/**
	 * Run.
	 *
	 * @param description the description
	 * @param scenario the scenario
	 */
	@Test(dataProvider = "dataProvider")
	private void run(String description, ScenarioSteps scenario) {
		if (null == scenario.getSteps())
			executeScenarioSteps(description, scenario);
		else
			executeMultiSteps(description, scenario);
	}

	/**
	 * Gets the test name.
	 *
	 * @return the test name
	 */
	@Override
	public String getTestName() {
		return testName.get();
	}

	/**
	 * Before method.
	 *
	 * @param testData the test data
	 * @param ctx the ctx
	 */
	@BeforeMethod
	public void beforeMethod(Object[] testData, ITestContext ctx) {
		testName.set(testData[0].toString());
		ctx.setAttribute("testName", testName.get());
	}

	/**
	 * Execute scenario steps.
	 *
	 * @param description the description
	 * @param scenario the scenario
	 */
	private void executeScenarioSteps(String description, ScenarioSteps scenario) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.addMethod(HttpMethod.valueOf(scenario.getRequest().getMethod().toUpperCase()))
				.addBaseUrl(scenario.getRequest().getUrl()).addHeader(scenario.getRequest().getHeaders())
				.addQueryParams(scenario.getRequest().getQueryparam()).addBody(scenario.getRequest().getTestdata());
		HttpResponse httpResponse = Request.execute(httpRequest);
		new ResponseValidator(scenario.getValidate(), httpResponse);
	}

	/**
	 * Execute multi steps.
	 *
	 * @param description the description
	 * @param scenario the scenario
	 */
	private void executeMultiSteps(String description, ScenarioSteps scenario) {
		Arrays.sort(scenario.getSteps(), Comparator.comparing(Steps::getStep));
		Map<String, HttpResponse> stepWithResponse = new HashMap<>();
		for (Steps mSteps : scenario.getSteps()) {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.addMethod(HttpMethod.valueOf(mSteps.getRequest().getMethod().toUpperCase()))
					.addBaseUrl(mStepClient.getUrl(mSteps.getRequest().getUrl(), stepWithResponse))
					.addHeader(mStepClient.getHeaders(mSteps.getRequest().getHeaders(), stepWithResponse))
					.addQueryParams(mStepClient.getQueryParams(mSteps.getRequest().getQueryparam(), stepWithResponse))
					.addBody(mStepClient.getRequestBody(mSteps.getRequest().getTestdata(), stepWithResponse));
			HttpResponse httpResponse = Request.execute(httpRequest);
			stepWithResponse.put(mSteps.getStep(), httpResponse);
			new ResponseValidator(mSteps.getValidate(), httpResponse);
		}
	}
}

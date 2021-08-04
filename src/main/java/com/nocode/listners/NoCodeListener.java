package com.nocode.listners;

import java.util.Arrays;
import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.collections.Lists;

import com.nocode.client.TestNGRunner;
import com.nocode.model.ScenarioSteps;
import com.nocode.utils.ILogger;
import com.nocode.utils.Logger;

public class NoCodeListener implements ISuiteListener, ITestListener, ILogger {

	private List<ITestResult> failureResults = Lists.newArrayList();

	@Override
	public void onStart(ISuite suite) {
		if (!isExecutionFlagFound()) {
			LOG.info("None of the test cases found with execution flag 'Y'");
			System.exit(0);
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		if (!failureResults.isEmpty()) {
			StringBuilder logFailedScenarios = new StringBuilder();
			logFailedScenarios.append(Logger.NEW_LINE);
			logFailedScenarios.append("=============== Failed Scenarios ==============");
			logFailedScenarios.append(Logger.NEW_LINE);
			for (ITestResult result : failureResults) {
				ScenarioSteps scenarioSteps = (ScenarioSteps) result.getParameters()[1];
				logFailedScenarios.append(scenarioSteps.getScenario());
				logFailedScenarios.append(Logger.NEW_LINE);
			}
			logFailedScenarios.append("===============================================");
			LOG.info(logFailedScenarios.toString());
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		failureResults.add(result);
	}

	private boolean isExecutionFlagFound() {
		return Arrays.stream(TestNGRunner.getInstance().getExecutionFile())
				.anyMatch(executionFile -> executionFile.getExecute().equalsIgnoreCase("Y"));
	}

}

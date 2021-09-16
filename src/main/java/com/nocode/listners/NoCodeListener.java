package com.nocode.listners;

import java.util.Arrays;
import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.collections.Lists;

import com.nocode.client.NoCodeClient;
import com.nocode.model.ScenarioSteps;
import com.nocode.utils.ILogger;
import com.nocode.utils.ReportLogger;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving noCode events.
 * The class that is interested in processing a noCode
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addNoCodeListener<code> method. When
 * the noCode event occurs, that object's appropriate
 * method is invoked.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class NoCodeListener implements ISuiteListener, ITestListener, ILogger {

	/** The failure results. */
	private List<ITestResult> failureResults = Lists.newArrayList();

	/**
	 * On start.
	 *
	 * @param suite the suite
	 */
	@Override
	public void onStart(ISuite suite) {
		if (!isExecutionFlagFound()) {
			LOG.info("None of the test cases found with execution flag 'Y'");
			System.exit(0);
		}
	}

	/**
	 * On finish.
	 *
	 * @param suite the suite
	 */
	@Override
	public void onFinish(ISuite suite) {
		if (!failureResults.isEmpty()) {
			StringBuilder logFailedScenarios = new StringBuilder();
			logFailedScenarios.append(ReportLogger.NEW_LINE);
			logFailedScenarios.append("=============== Failed Scenarios ==============");
			logFailedScenarios.append(ReportLogger.NEW_LINE);
			for (ITestResult result : failureResults) {
				ScenarioSteps scenarioSteps = (ScenarioSteps) result.getParameters()[1];
				logFailedScenarios.append(scenarioSteps.getScenario());
				logFailedScenarios.append(ReportLogger.NEW_LINE);
			}
			logFailedScenarios.append("===============================================");
			LOG.info(logFailedScenarios.toString());
		}
	}

	/**
	 * On test failure.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		failureResults.add(result);
	}

	/**
	 * Checks if is execution flag found.
	 *
	 * @return true, if is execution flag found
	 */
	private boolean isExecutionFlagFound() {
		return Arrays.stream(new NoCodeClient().getExecutionScenarios())
				.anyMatch(executionFile -> executionFile.getExecute().equalsIgnoreCase("Y"));
	}

}

package com.nocode.utils;

import static com.nocode.config.ExtentReportManager.getCurrentTest;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class is responsible for Reporting.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class ReportUtil {

	/**
	 * Log info.
	 *
	 * @param details the details
	 */
	public static void logInfo(String details) {
		getCurrentTest().log(Status.INFO, details);
	}

	/**
	 * Log message.
	 *
	 * @param status the status
	 * @param details the details
	 */
	public static void logMessage(Status status, String details) {
		getCurrentTest().log(status, details);
	}

	/**
	 * Log exception.
	 *
	 * @param t the t
	 */
	public static void logException(Throwable t){
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		getCurrentTest().fail(MarkupHelper.createCodeBlock(stringWriter.toString()));
	}
	
	/**
	 * Log req res.
	 *
	 * @param status the status
	 * @param json the json
	 */
	public static void logReqRes(String json) {
		getCurrentTest().info(MarkupHelper.createCodeBlock(json));
	}
}

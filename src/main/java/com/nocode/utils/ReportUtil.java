package com.nocode.utils;

import static com.nocode.config.ExtentReportManager.getCurrentTest;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/**
 * The Class is responsible for Reporting
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class ReportUtil {

	public static void logInfo(String details) {
		getCurrentTest().log(Status.INFO, details);
	}

	public static void logMessage(Status status, String details) {
		getCurrentTest().log(status, details);
	}

	public static void logException(Throwable t){
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		getCurrentTest().fail(MarkupHelper.createCodeBlock(stringWriter.toString()));
	}
	
	public static void logReqRes(Status status, String json) {
		getCurrentTest().info(MarkupHelper.createCodeBlock(json));
	}
}

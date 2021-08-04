package com.nocode;

import org.testng.TestNG;

import com.nocode.client.TestNGRunner;
import com.nocode.listners.NoCodeListener;

/**
 * 
 *
 */
public class NoCodeRunner {
	
	private NoCodeRunner(){}

	public static void run() {
		TestNG suite = new TestNG();
		suite.addListener(new NoCodeListener());
		suite.setVerbose(2);
		suite.setOutputDirectory("test-output");
		suite.setTestClasses(new Class[] { TestNGRunner.class });
		suite.run();
	}
}

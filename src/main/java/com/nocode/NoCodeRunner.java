package com.nocode;

import com.nocode.client.TestNGRunner;

/**
 * 
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
public class NoCodeRunner {

	public static void run() {
		TestNGRunner.getInstance().runTestClass();
	}

	private NoCodeRunner() {
	}
}

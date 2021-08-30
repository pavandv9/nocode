package com.nocode.constants;

/**
 * 
 * @author Pavan.DV
 *
 * @since 1.0.0
 */
public final class AssertMessage {

	public static final String STATUS_CODE_FAILED = "Status code not matching";
	public static final String RESPONSE_BODY_FAILED = "Response body not matching";
	public static final String RESPONSE_BODY_EMPTY_FAILED = "Response body not matching expected to be empty but found not empty";
	public static final String RESPONSE_BODY_NOT_EMPTY_FAILED = "Response body not matching expected to be not empty but found empty";

	private AssertMessage() {}
}

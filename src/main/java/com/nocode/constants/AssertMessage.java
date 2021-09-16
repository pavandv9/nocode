package com.nocode.constants;

// TODO: Auto-generated Javadoc
/**
 * The Class AssertMessage.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public final class AssertMessage {

	/** The Constant STATUS_CODE_FAILED. */
	public static final String STATUS_CODE_FAILED = "Status code not matching";
	
	/** The Constant RESPONSE_BODY_FAILED. */
	public static final String RESPONSE_BODY_FAILED = "Response body not matching";
	
	/** The Constant RESPONSE_BODY_EMPTY_FAILED. */
	public static final String RESPONSE_BODY_EMPTY_FAILED = "Response body not matching expected to be empty but found not empty";
	
	/** The Constant RESPONSE_BODY_NOT_EMPTY_FAILED. */
	public static final String RESPONSE_BODY_NOT_EMPTY_FAILED = "Response body not matching expected to be not empty but found empty";

	/**
	 * Instantiates a new assert message.
	 */
	private AssertMessage() {}
}

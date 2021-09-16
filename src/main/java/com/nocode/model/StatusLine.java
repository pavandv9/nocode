/**
 * 
 */
package com.nocode.model;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusLine.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class StatusLine {

	/** The status code. */
	private int statusCode;
	
	/** The status message. */
	private String statusMessage;

	/**
	 * Instantiates a new status line.
	 *
	 * @param statusCode the status code
	 * @param statusMessage the status message
	 */
	public StatusLine(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
	
	/**
	 * Get status code from the response.
	 *
	 * @return status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Get status message from the response.
	 *
	 * @return status message
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "StatusLine [statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}

}

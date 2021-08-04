/**
 * 
 */
package com.nocode.model;

/**
 * @author Pavan.DV
 * @since 1.0.0
 */
public class StatusLine {

	private int statusCode;
	private String statusMessage;

	public StatusLine(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
	
	/**
	 * Get status code from the response
	 * @return status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Get status message from the response
	 * @return status message
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	@Override
	public String toString() {
		return "StatusLine [statusCode=" + statusCode + ", statusMessage=" + statusMessage + "]";
	}

}

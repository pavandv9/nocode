/**
 * 
 */
package com.nocode.exception;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpException.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class HttpException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new http exception.
	 *
	 * @param e the e
	 */
	public HttpException(@NonNull Throwable e) {
		super(e);
	}

	/**
	 * Instantiates a new http exception.
	 *
	 * @param message the message
	 */
	public HttpException(@NonNull String message) {
		super(message);
	}

	/**
	 * Instantiates a new http exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public HttpException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}
}

/**
 * 
 */
package com.nocode.exception;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class MailException.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class MailException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -922345673459661827L;

	/**
	 * Instantiates a new mail exception.
	 *
	 * @param e the e
	 */
	public MailException(@NonNull Throwable e) {
		super(e);
	}

	/**
	 * Instantiates a new mail exception.
	 *
	 * @param message the message
	 */
	public MailException(@NonNull String message) {
		super(message);
	}

	/**
	 * Instantiates a new mail exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public MailException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}
}

/**
 * 
 */
package com.nocode.exception;

import lombok.NonNull;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseException.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
public class DatabaseException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4975838595834391161L;

	/**
	 * Instantiates a new database exception.
	 *
	 * @param e the e
	 */
	public DatabaseException(@NonNull Throwable e) {
		super(e);
	}

	/**
	 * Instantiates a new database exception.
	 *
	 * @param message the message
	 */
	public DatabaseException(@NonNull String message) {
		super(message);
	}

	/**
	 * Instantiates a new database exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public DatabaseException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}

}

/**
 * 
 */
package com.nocode.exception;

import lombok.NonNull;

/**
 * @author Pavan.DV
 * @since 1.0.0
 *
 */
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 4975838595834391161L;

	public DatabaseException(@NonNull Throwable e) {
		super(e);
	}

	public DatabaseException(@NonNull String message) {
		super(message);
	}

	public DatabaseException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}

}

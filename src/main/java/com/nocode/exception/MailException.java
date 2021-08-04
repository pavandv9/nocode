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
public class MailException extends RuntimeException {

	private static final long serialVersionUID = -922345673459661827L;

	public MailException(@NonNull Throwable e) {
		super(e);
	}

	public MailException(@NonNull String message) {
		super(message);
	}

	public MailException(@NonNull String message, @NonNull Throwable throwable) {
		super(message, throwable);
	}
}

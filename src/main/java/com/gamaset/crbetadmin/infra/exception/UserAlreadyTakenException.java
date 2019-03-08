package com.gamaset.crbetadmin.infra.exception;

public class UserAlreadyTakenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyTakenException(final String message) {
		super(message);
	}

	public UserAlreadyTakenException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyTakenException(final Throwable cause) {
		super(cause);
	}

}

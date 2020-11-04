package com.academy.base.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BaseException(final String message) {
		super(message);
	}

	public BaseException(final String message, final Exception source) {
		super(message, source);
	}
}

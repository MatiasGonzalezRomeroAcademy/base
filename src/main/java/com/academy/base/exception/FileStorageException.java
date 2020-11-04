package com.academy.base.exception;

public class FileStorageException extends BaseException {
	private static final long serialVersionUID = 1L;

	public FileStorageException(final String message) {
		super(message);
	}

	public FileStorageException(final String message, final Exception source) {
		super(message, source);
	}
}
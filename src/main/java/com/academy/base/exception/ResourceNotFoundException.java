package com.academy.base.exception;

public class ResourceNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(final String resourceName, final String fieldName, final Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
	}
}

package com.academy.base.exception;

public class InvalidCalculatorArgument extends BaseException {
	private static final long serialVersionUID = 1L;

	public InvalidCalculatorArgument(final String message) {
		super(message);
	}
}

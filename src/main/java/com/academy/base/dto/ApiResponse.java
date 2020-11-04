package com.academy.base.dto;

import java.io.Serializable;

public class ApiResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean success;
	private String message;

	public ApiResponse(final Boolean success, final String message) {
		this.success = success;
		this.message = message;
	}

	public ApiResponse(final String message) {
		success = true;
		this.message = message;
	}
}

package com.academy.base.dto.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
	@NotBlank(message = "Cannot be blank")
	@Size(min = 4, max = 40, message = "The size must be between {min} and {max}. Length found : ${validatedValue.length()}")
	private String name;

	@NotBlank(message = "Cannot be blank")
	@Size(min = 3, max = 15, message = "The size must be between {min} and {max}. Length found : ${validatedValue.length()}")
	private String username;

	@NotBlank(message = "Cannot be blank")
	@Size(max = 40, message = "Exceeds the value of {max}. Length found : ${validatedValue.length()}")
	@Email(message = "Should be a valid email address")
	private String email;

	@NotBlank
	@Size(min = 6, max = 20, message = "the Size must be between {min} and {max}. Length found : ${validatedValue.length()}")
	private String password;
}

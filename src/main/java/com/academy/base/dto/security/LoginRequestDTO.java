package com.academy.base.dto.security;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel(description = "Class representing a login request of the app")
public class LoginRequestDTO {

	@NotNull
	private String username;

	@NotNull
	private String password;
}

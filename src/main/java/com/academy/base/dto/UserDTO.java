package com.academy.base.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Class representing of an app user")
public class UserDTO {

	protected Long id;

	@NotBlank(message = "{message.error.empty}")
	@Size(max = 15, message = "{message.error.size-max}")
	@ApiModelProperty(notes = "Name of the new user", example = "John Doe", required = true)
	protected String username;

	@NotBlank(message = "{message.error.empty}")
	@Size(min = 6, max = 20, message = "The size must be between {min} and {max}. Length found : ${validatedValue.length()}")
	private String password;
}

package com.academy.base.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String description;

	@NotEmpty(message = "Project id could not be empty")
	private Long projectId;
}

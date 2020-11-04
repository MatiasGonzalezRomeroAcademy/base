package com.academy.base.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTaskDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;
}

package com.academy.base.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

}

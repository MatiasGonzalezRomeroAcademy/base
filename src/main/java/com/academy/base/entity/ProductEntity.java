package com.academy.base.entity;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(indexName = "products-index")
public class ProductEntity {

	@Id
	private String id;

	@Column(length = 255)
	private String name;

	@Column(length = 255)
	private String description;
}

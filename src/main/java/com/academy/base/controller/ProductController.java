package com.academy.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.base.dto.ProductDTO;
import com.academy.base.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private ProductService productService;

	@Autowired
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ProductDTO create(@Validated @RequestBody final ProductDTO product) {
		return productService.save(product);
	}

	@GetMapping
	public Page<ProductDTO> index( //
			@RequestParam(required = false) String search, //
			final Pageable pageable //
	) {
		return productService.fuzzySearch(search, pageable);
	}

	@DeleteMapping
	public void deleteAll() {
		productService.deleteAll();
	}
}

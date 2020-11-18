package com.academy.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academy.base.dto.ProductDTO;
import com.academy.base.entity.ProductEntity;
import com.academy.base.repository.ProductRepository;

@Service
public class ProductService {
	private ProductRepository elasticSearchProductRepository;

	@Autowired
	public ProductService(final ProductRepository productRepository) {
		this.elasticSearchProductRepository = productRepository;
	}

	@Transactional
	public Page<ProductDTO> findAll(final Pageable pageable) {
		return elasticSearchProductRepository.findAll(pageable).map(this::toDTO);
	}

	@Transactional
	public Page<ProductDTO> fuzzySearch(final String searchTerm, final Pageable pageable) {
		if (searchTerm == null) {
			return elasticSearchProductRepository.findAll(pageable).map(this::toDTO);
		}

		return elasticSearchProductRepository.findByNameLikeIgnoreCase(searchTerm, pageable).map(this::toDTO);
	}

	@Transactional
	public ProductDTO save(final ProductDTO dto) {
		return toDTO(elasticSearchProductRepository.save(toEntity(dto)));
	}

	public void deleteAll() {
		elasticSearchProductRepository.deleteAll();
	}

	private ProductEntity toEntity(final ProductDTO dto) {
		final ProductEntity entity = new ProductEntity();

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());

		return entity;
	}

	private ProductDTO toDTO(final ProductEntity entity) {
		final ProductDTO dto = new ProductDTO();

		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());

		return dto;
	}
}

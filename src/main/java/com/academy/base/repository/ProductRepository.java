package com.academy.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.base.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	List<ProductEntity> findByName(final String name);

	@Override
	Page<ProductEntity> findAll(final Pageable pageable);

	Page<ProductEntity> findByNameLikeIgnoreCase(final String name, final Pageable pageable);
}

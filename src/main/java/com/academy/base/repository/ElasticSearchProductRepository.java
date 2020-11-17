package com.academy.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.academy.base.entity.ProductEntity;

public interface ElasticSearchProductRepository extends ElasticsearchRepository<ProductEntity, String> {

	List<ProductEntity> findByName(final String name);

	@Override
	Page<ProductEntity> findAll(final Pageable pageable);

	@Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name\", \"description\"], \"fuzziness\": \"AUTO\"}}")
	Page<ProductEntity> findFuzzy(final String search, final Pageable pageable);
}

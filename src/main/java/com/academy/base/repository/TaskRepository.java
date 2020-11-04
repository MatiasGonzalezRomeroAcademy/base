package com.academy.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.base.entity.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

	@Override
	public List<TaskEntity> findAll();

	@Override
	public boolean existsById(final Long id);

	public Page<TaskEntity> findByDescriptionLikeIgnoreCase(final String description, final Pageable pageable);

}

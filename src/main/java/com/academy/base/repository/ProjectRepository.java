package com.academy.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.base.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

	@Override
	public List<ProjectEntity> findAll();

	@Override
	public Optional<ProjectEntity> findById(final Long id);

	@Override
	public boolean existsById(final Long id);

}

package com.academy.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.base.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Override
	Page<UserEntity> findAll(final Pageable pageable);

	List<UserEntity> findByIdIn(final List<Long> userIds);

	Optional<UserEntity> findByUsername(final String username);

	Boolean existsByUsername(final String username);
}
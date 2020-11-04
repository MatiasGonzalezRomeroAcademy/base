package com.academy.base.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academy.base.dto.UserDTO;
import com.academy.base.entity.RoleEntity;
import com.academy.base.entity.UserEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService( //
			final UserRepository userRepository, //
			final PasswordEncoder passwordEncoder //
	) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserDTO save(final UserDTO user) {
		return toDTO(userRepository.save(toEntity(user)));
	}

	@Transactional(readOnly = true)
	public UserDTO getById(final Long id) {
		return userRepository.findById(id) //
				.map(this::toDTO) //
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> getAll(final Pageable pageable) {
		return userRepository.findAll(pageable) //
				.map(this::toDTO);
	}

	@Transactional(readOnly = true)
	public UserDTO getByUserName(final String userName) {
		return userRepository.findByUsername(userName) //
				.map(this::toDTO) //
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", userName));
	}

	@Transactional
	public ResponseEntity<UserDTO> delete(final Long id) {
		return null;
	}

	@Transactional
	public Boolean existsByUsername(final String userName) {
		return userRepository.existsByUsername(userName);
	}

	@Transactional
	public UserDTO createUserAccount(final UserDTO signUp) {
		final UserEntity user = toEntity(signUp);
		user.setEnabled(true);

		user.setPassword(passwordEncoder.encode(signUp.getPassword()));

		final RoleEntity roleUser = new RoleEntity();
		roleUser.setAuthority("ROLE_USER");
		roleUser.setUser(user);

		user.setRoles(Collections.singletonList(roleUser));

		return toDTO(userRepository.save(user));
	}

	private UserDTO toDTO(final UserEntity entity) {
		final UserDTO dto = new UserDTO();

		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());

		return dto;
	}

	private UserEntity toEntity(final UserDTO dto) {
		final UserEntity entity = new UserEntity();

		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());

		return entity;
	}
}

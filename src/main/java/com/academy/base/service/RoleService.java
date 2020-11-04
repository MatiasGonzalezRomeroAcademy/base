package com.academy.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.base.entity.RoleEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	public RoleEntity findByName(final String roleName) {
		return roleRepository.findByAuthority(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "role name", roleName));
	}
}

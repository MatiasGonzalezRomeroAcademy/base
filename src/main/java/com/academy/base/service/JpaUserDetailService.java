package com.academy.base.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academy.base.entity.UserEntity;
import com.academy.base.exception.ResourceNotFoundException;
import com.academy.base.repository.UserRepository;

@Service
public class JpaUserDetailService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public JpaUserDetailService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) {
		final UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

		final List<GrantedAuthority> authorities = user.getRoles().stream() //
				.map(role -> new SimpleGrantedAuthority(role.getAuthority())) //
				.collect(Collectors.toList());

		return new User( //
				user.getUsername(), //
				user.getPassword(), //
				user.getEnabled(), //
				true, // accountNonExpired
				true, // credentialsNonExpired
				true, // accountNonLocked
				authorities //
		);
	}

}

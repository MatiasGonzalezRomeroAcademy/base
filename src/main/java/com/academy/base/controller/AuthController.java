package com.academy.base.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.base.dto.UserDTO;
import com.academy.base.dto.security.LoginRequestDTO;
import com.academy.base.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserService userService;

	public AuthController(final UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public UserDTO registerUser(@RequestBody @Valid final UserDTO signUpRequest) {
		return userService.createUserAccount(signUpRequest);
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@Valid @RequestBody final LoginRequestDTO loginRequest) {

		// delegate the work to JwtAuthenticationFilter class

		return ResponseEntity.ok("Loged in");
	}
}

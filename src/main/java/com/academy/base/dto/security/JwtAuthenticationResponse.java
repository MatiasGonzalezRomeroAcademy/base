package com.academy.base.dto.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	public JwtAuthenticationResponse(final String accessToken) {
		this.accessToken = accessToken;
	}
}

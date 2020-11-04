package com.academy.base.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private JwtTokenManager tokenProvider;

	public JwtAuthorizationFilter( //
			final AuthenticationManager authenticationManager, final ApplicationContext applicationContext //
	) {
		super(authenticationManager);
		tokenProvider = applicationContext.getBean(JwtTokenManager.class);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader("Authorization");

		if (!hasAuthorizationToken(authorizationHeader)) {
			chain.doFilter(request, response);
			return;
		}

		final String token = authorizationHeader.substring(7);

		boolean isTokenValid = false;
		Claims tokenPayload = null;
		try {

			tokenPayload = tokenProvider.validateToken(token);

			isTokenValid = true;
		} catch (final JwtException | IllegalArgumentException ex) {
			isTokenValid = false;
		}

		UsernamePasswordAuthenticationToken authentication = null;
		if (isTokenValid) {
			String username = tokenPayload.getSubject();
			Object roles = tokenPayload.get("authorities");
			Collection<? extends GrantedAuthority> authorities = Arrays.asList( //
					new ObjectMapper() //
							.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)//
							.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));

			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}

		// authenticate user the current request
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private boolean hasAuthorizationToken(final String header) {
		return (header != null && header.startsWith("Bearer "));
	}
}

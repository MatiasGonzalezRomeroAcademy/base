package com.academy.base.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.academy.base.properties.JwtSecurityProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenManager {

	@Autowired
	private JwtSecurityProperties properties;

	public String generateToken(final User user) throws JsonProcessingException {
		final String secretKey = properties.getSecretKey();

		final Claims claims = generateClaims(user);

		return Jwts.builder()//
				.setSubject(user.getUsername()) //
				.signWith(getSigningKey(secretKey)) //
				.setIssuedAt(new Date(System.currentTimeMillis())) //
				.setExpiration(new Date(System.currentTimeMillis() + 14000000L)) //
				.setClaims(claims) //
				.compact();

	}

	public Claims validateToken(final String token) {
		final String secretKey = properties.getSecretKey();

		return Jwts.parser() //
				.setSigningKey(secretKey.getBytes()) //
				.parseClaimsJws(token).getBody();
	}

	private Claims generateClaims(final User user) throws JsonProcessingException {
		final Claims claims = Jwts.claims();
		final Collection<? extends GrantedAuthority> roles = user.getAuthorities();

		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		claims.put("username", user.getUsername());

		return claims;
	}

	private Key getSigningKey(final String secretKey) {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

		return Keys.hmacShaKeyFor(keyBytes);
	}

}

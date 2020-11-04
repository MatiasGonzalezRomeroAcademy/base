package com.academy.base.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
	private static final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**") //
				.allowedOrigins("*") // change the *, instead use a white list
				.allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE") //
				.maxAge(MAX_AGE_SECS);
	}
}

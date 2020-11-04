package com.academy.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class JwtSecurityProperties {

	private String secretKey;

	private String jwtExpirationInMs;
}

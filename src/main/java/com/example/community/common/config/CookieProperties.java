package com.example.community.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cookie")
public record CookieProperties(
	Long maxAge,
	String path,
	boolean httpOnly,
	boolean secure
) {
}

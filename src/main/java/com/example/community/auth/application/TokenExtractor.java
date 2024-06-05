package com.example.community.auth.application;

public interface TokenExtractor {

	Long extractAccessToken(String token);

	String extractRefreshToken(String token);
}

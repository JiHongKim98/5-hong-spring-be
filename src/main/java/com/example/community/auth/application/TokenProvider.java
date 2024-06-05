package com.example.community.auth.application;

public interface TokenProvider {

	String generatedAccessToken(Long memberId);

	String generatedRefreshToken(String tokenId);
}

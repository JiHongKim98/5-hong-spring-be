package com.example.community.auth.application.dto;

public record LogoutRequest(
	String refreshToken
) {
}

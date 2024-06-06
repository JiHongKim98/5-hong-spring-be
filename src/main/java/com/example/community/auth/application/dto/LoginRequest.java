package com.example.community.auth.application.dto;

public record LoginRequest(
	String email,
	String password
) {
}

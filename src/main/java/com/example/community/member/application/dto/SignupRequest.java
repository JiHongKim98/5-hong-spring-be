package com.example.community.member.application.dto;

public record SignupRequest(
	String email,
	String password,
	String nickname,
	String profileImage
) {
}

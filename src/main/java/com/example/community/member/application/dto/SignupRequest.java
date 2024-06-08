package com.example.community.member.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
	@NotBlank String email,
	@NotBlank String password,
	@NotBlank String nickname,
	@NotBlank String profileImage
) {
}

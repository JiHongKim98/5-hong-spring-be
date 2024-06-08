package com.example.community.member.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
	@NotBlank String password
) {
}

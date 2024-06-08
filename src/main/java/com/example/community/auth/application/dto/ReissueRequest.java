package com.example.community.auth.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ReissueRequest(
	@NotBlank String refreshToken
) {
}

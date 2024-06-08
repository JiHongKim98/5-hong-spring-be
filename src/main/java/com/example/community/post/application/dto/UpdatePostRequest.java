package com.example.community.post.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequest(
	@NotBlank String title,
	@NotBlank String contents,
	@NotBlank String thumbnail
) {
}

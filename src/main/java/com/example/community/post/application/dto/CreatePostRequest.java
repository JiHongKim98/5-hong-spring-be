package com.example.community.post.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(
	@NotBlank String title,
	@NotBlank String contents,
	String thumbnail
) {
}

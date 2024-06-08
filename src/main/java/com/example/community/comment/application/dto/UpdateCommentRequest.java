package com.example.community.comment.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
	@NotBlank String contents
) {
}

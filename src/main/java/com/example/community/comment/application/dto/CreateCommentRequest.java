package com.example.community.comment.application.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
	@NotBlank Long postId,
	@NotBlank String contents
) {
}

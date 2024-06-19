package com.example.community.comment.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(
	@NotNull Long postId,
	@NotBlank String contents
) {
}

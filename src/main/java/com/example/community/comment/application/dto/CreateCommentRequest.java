package com.example.community.comment.application.dto;

public record CreateCommentRequest(
	Long postId,
	String contents
) {
}

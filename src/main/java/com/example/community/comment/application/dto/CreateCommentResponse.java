package com.example.community.comment.application.dto;

import com.example.community.comment.domain.Comment;

public record CreateCommentResponse(
	Long commentId
) {

	public static CreateCommentResponse of(Comment comment) {
		return new CreateCommentResponse(comment.getId());
	}
}

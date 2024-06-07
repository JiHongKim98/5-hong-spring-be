package com.example.community.comment.application.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

public record PagedCommentResponse(
	boolean hasNext,
	Long nextCursor,
	List<CommentDetailResponse> data
) {

	public static PagedCommentResponse of(Slice<CommentDetailResponse> slice) {
		List<CommentDetailResponse> content = slice.getContent();
		Long nextCursor = slice.hasNext() && !content.isEmpty()
			? content.get(content.size() - 1).commentId()
			: null;
		return new PagedCommentResponse(slice.hasNext(), nextCursor, content);
	}
}

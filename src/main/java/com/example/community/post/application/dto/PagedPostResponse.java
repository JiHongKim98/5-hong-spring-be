package com.example.community.post.application.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

// TODO: utils 패키지로 빼기 (게시글, 댓글)
public record PagedPostResponse(
	boolean hasNext,
	Long nextCursor,
	List<PostDetailResponse> data
) {

	public static PagedPostResponse of(Slice<PostDetailResponse> slice) {
		List<PostDetailResponse> content = slice.getContent();
		Long nextCursor = slice.hasNext() && !content.isEmpty()
			? content.get(content.size() - 1).postId()
			: null;
		return new PagedPostResponse(slice.hasNext(), nextCursor, content);
	}
}


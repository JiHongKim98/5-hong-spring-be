package com.example.community.post.application.dto;

import com.example.community.post.domain.Post;

public record CreatePostResponse(
	Long postId
) {

	public static CreatePostResponse of(Post post) {
		return new CreatePostResponse(post.getId());
	}
}

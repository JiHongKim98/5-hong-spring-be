package com.example.community.post.application.dto;

public record CreatePostRequest(
	String title,
	String contents,
	String thumbnail
) {
}

package com.example.community.post.application.dto;

public record UpdatePostRequest(
	String title,
	String contents,
	String thumbnail
) {
}

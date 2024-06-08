package com.example.community.upload.application.dto;

public record UploadImageResponse(
	String image
) {

	public static UploadImageResponse of(String imageUrl) {
		return new UploadImageResponse(imageUrl);
	}
}

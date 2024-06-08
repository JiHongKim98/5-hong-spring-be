package com.example.community.upload.application;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.community.upload.application.dto.UploadImageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadService {

	private final ImageClient imageClient;

	public UploadImageResponse uploadImage(MultipartFile file) {
		String objectKey = UUID.randomUUID().toString();
		String imageUrl = imageClient.upload(objectKey, file);
		return UploadImageResponse.of(imageUrl);
	}
}

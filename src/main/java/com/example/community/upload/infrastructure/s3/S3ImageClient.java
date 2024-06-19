package com.example.community.upload.infrastructure.s3;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.community.common.config.S3Properties;
import com.example.community.upload.application.ImageClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3ImageClient implements ImageClient {

	// TODO: Async 기반으로 해야할듯 -> 이미지 저장 이벤트 발행

	private final S3Uploader s3Uploader;
	private final S3Properties properties;

	@Override
	public String upload(String objectKey, MultipartFile file) {
		String bucketName = properties.bucketName();
		s3Uploader.upload(bucketName, objectKey, file);
		return generatedImageUrl(objectKey);
	}

	// image 가 저장된 URL 생성
	private String generatedImageUrl(String objectKey) {
		return properties.imageUrl() + objectKey;
	}
}

package com.example.community.upload.infrastructure.s3;

import static com.example.community.upload.exception.UploadExceptionType.*;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.community.upload.exception.UploadException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3Uploader {

	// S3 캐시 30일
	private static final String CACHE_CONTROL = "max-age=2592000";  // 30 * 24 * 60 * 60 (초)

	private final S3Client s3Client;

	public void upload(String bucketName, String key, MultipartFile file) {
		PutObjectRequest request = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(key)
			.contentType(file.getContentType())
			.cacheControl(CACHE_CONTROL)
			.build();

		putObject(request, file);
	}

	private void putObject(PutObjectRequest request, MultipartFile file) {
		try {
			s3Client.putObject(request, RequestBody.fromBytes(getBytes(file)));
		} catch (Exception ex) {
			throw new UploadException(UPLOAD_IMAGE_FAIL);
		}
	}

	private byte[] getBytes(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException ex) {
			throw new UploadException(FILE_ENCODE_FAIL);
		}
	}
}

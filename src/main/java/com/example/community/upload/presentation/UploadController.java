package com.example.community.upload.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.community.common.annotation.LoginRequired;
import com.example.community.upload.application.UploadService;
import com.example.community.upload.application.dto.UploadImageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/uploads")
@RequiredArgsConstructor
public class UploadController {

	private final UploadService uploadService;

	@LoginRequired
	@PostMapping("/image")
	public ResponseEntity<UploadImageResponse> uploadImage(
		@RequestParam("file") MultipartFile file
	) {
		return ResponseEntity.ok(uploadService.uploadImage(file));
	}
}

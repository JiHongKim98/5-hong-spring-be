package com.example.community.upload.exception;

import org.springframework.http.HttpStatus;

import com.example.community.common.exception.ExceptionType;

public enum UploadExceptionType implements ExceptionType {
	ENCODED_FAIL_ERROR("", HttpStatus.BAD_REQUEST),
	UPLOAD_FAIL_ERROR("", HttpStatus.BAD_REQUEST),  // 400 응답으로 하는게 좋으려나..?
	;

	private final String message;
	private final HttpStatus status;

	UploadExceptionType(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public String code() {
		return this.name();
	}

	@Override
	public HttpStatus status() {
		return status;
	}
}

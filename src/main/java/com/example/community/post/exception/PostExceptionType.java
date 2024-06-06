package com.example.community.post.exception;

import org.springframework.http.HttpStatus;

import com.example.community.common.exception.ExceptionType;

public enum PostExceptionType implements ExceptionType {
	NOT_EXIST_POST("존재하지 않는 게시글입니다.", HttpStatus.NOT_FOUND),
	NOT_OWNER("해당 게시글에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
	;

	private final String message;
	private final HttpStatus status;

	PostExceptionType(String message, HttpStatus status) {
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

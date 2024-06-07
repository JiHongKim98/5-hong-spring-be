package com.example.community.comment.exception;

import org.springframework.http.HttpStatus;

import com.example.community.common.exception.ExceptionType;

public enum CommentExceptionType implements ExceptionType {
	NOT_EXIST_COMMENT("존재하지 않는 댓글입니다.", HttpStatus.BAD_REQUEST),
	NOT_OWNER("해당 댓글에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
	;

	private final String message;
	private final HttpStatus status;

	CommentExceptionType(String message, HttpStatus status) {
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

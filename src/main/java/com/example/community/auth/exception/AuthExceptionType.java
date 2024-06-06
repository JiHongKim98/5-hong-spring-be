package com.example.community.auth.exception;

import org.springframework.http.HttpStatus;

import com.example.community.common.exception.ExceptionType;

public enum AuthExceptionType implements ExceptionType {
	UNAUTHORIZED("인증 정보가 제공되지 않았습니다.", HttpStatus.UNAUTHORIZED),
	EXPIRED_TOKEN("이미 만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN("유효하지 않는 토큰입니다.", HttpStatus.BAD_REQUEST),
	INVALID_TOKEN_TYPE("유효하지 않는 토큰 타입입니다.", HttpStatus.BAD_REQUEST),
	UN_MATCHED_AUTHORIZATION("일치하지 않는 사용자 입니다.", HttpStatus.UNAUTHORIZED),
	;

	private final String message;
	private final HttpStatus status;

	AuthExceptionType(String message, HttpStatus status) {
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

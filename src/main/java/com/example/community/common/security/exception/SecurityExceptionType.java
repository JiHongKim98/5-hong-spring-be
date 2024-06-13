package com.example.community.common.security.exception;

public enum SecurityExceptionType {
	UNAUTHORIZED("인증 정보가 제공되지 않았습니다."),
	EXPIRED_TOKEN("이미 만료된 토큰입니다."),
	;

	private final String message;

	SecurityExceptionType(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}
}

package com.example.community.member.exception;

import org.springframework.http.HttpStatus;

import com.example.community.common.exception.ExceptionType;

public enum MemberExceptionType implements ExceptionType {
	NOT_EXIST_MEMBER("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
	NOT_EXIST_EMAIL("존재하지 않는 이메일입니다.", HttpStatus.NOT_FOUND),
	INVALID_PASSWORD("비밀번호가 틀립니다.", HttpStatus.UNAUTHORIZED),
	;

	private final String message;
	private final HttpStatus status;

	MemberExceptionType(String message, HttpStatus status) {
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

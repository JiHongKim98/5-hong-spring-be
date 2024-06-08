package com.example.community.common.exception.redis;

import org.springframework.http.HttpStatus;

import com.example.community.common.exception.ExceptionType;

public enum RedisExceptionType implements ExceptionType {
	SERIALIZE_ERROR("직렬화 과정중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
	DESERIALIZE_ERROR("역직렬화 과정중 오류가 발생했습니다", HttpStatus.BAD_REQUEST),
	;

	private final HttpStatus status;
	private final String message;

	RedisExceptionType(String message, HttpStatus status) {
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

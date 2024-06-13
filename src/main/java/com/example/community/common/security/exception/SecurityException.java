package com.example.community.common.security.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class SecurityException extends AuthenticationException {

	public SecurityException(SecurityExceptionType exceptionType) {
		super(exceptionType.message());
	}

	public SecurityException(SecurityExceptionType exceptionType, Throwable cause) {
		super(exceptionType.message(), cause);
	}
}

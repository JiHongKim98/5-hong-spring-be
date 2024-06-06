package com.example.community.auth.exception;

import com.example.community.common.exception.BaseException;

public class AuthException extends BaseException {

	public AuthException(AuthExceptionType exceptionType) {
		super(exceptionType);
	}
}

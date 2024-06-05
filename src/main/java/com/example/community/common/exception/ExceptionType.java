package com.example.community.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

	String message();

	String code();

	HttpStatus status();
}

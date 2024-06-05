package com.example.community.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final static String SERVER_ERROR_MESSAGE = "INTERNAL_SERVER_ERROR";

	// Custom Exception 응답
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ExceptionResponse> handleException(BaseException ex) {
		log.warn(ex.getMessage(), ex);

		ExceptionType type = ex.getType();
		return ResponseEntity.status(type.status())
			.body(new ExceptionResponse(type.code(), ex.getMessage()));
	}

	// Custom Exception 이외의 예외 응답
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);

		return ResponseEntity.internalServerError()
			.body(new ExceptionResponse(SERVER_ERROR_MESSAGE, ex.getMessage()));
	}
}

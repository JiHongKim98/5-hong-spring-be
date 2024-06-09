package com.example.community.common.exception;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String SERVER_ERROR_MESSAGE = "INTERNAL_SERVER_ERROR";
	private static final String FILE_SIZE_EXCEEDED_MESSAGE = "FILE_SIZE_EXCEEDED";
	private static final String MISSING_REQUIRED_FIELDS_MESSAGE = "ARGUMENT_NOT_VALID";

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

	// 전역 유효성 검사 예외 처리 (validation)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request
	) {
		log.warn(ex.getMessage(), ex);

		String message = Objects.requireNonNull(ex.getBindingResult().getFieldError())
			.getDefaultMessage();
		return ResponseEntity.status(status)
			.body(new ExceptionResponse(MISSING_REQUIRED_FIELDS_MESSAGE, message));
	}

	// MaxUploadSizeExceededException 예외 응답 오버라이드
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
		Exception ex,
		Object body,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request
	) {
		if (ex instanceof MaxUploadSizeExceededException) {
			log.warn(ex.getMessage(), ex);

			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)  // 413 RESPONSE
				.body(new ExceptionResponse(FILE_SIZE_EXCEEDED_MESSAGE, ex.getMessage()));
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}

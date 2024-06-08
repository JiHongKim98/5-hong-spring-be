package com.example.community.upload.exception;

import com.example.community.common.exception.BaseException;
import com.example.community.common.exception.ExceptionType;

public class UploadException extends BaseException {

	public UploadException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}

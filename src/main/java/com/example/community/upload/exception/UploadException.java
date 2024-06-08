package com.example.community.upload.exception;

import com.example.community.common.exception.BaseException;

public class UploadException extends BaseException {

	public UploadException(UploadExceptionType exceptionType) {
		super(exceptionType);
	}
}

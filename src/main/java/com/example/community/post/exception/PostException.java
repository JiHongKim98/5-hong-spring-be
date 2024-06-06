package com.example.community.post.exception;

import com.example.community.common.exception.BaseException;

public class PostException extends BaseException {

	public PostException(PostExceptionType exceptionType) {
		super(exceptionType);
	}
}

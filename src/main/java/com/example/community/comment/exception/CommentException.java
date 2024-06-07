package com.example.community.comment.exception;

import com.example.community.common.exception.BaseException;

public class CommentException extends BaseException {

	public CommentException(CommentExceptionType exceptionType) {
		super(exceptionType);
	}
}

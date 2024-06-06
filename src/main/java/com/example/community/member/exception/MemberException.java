package com.example.community.member.exception;

import com.example.community.common.exception.BaseException;
import com.example.community.common.exception.ExceptionType;

public class MemberException extends BaseException {

	public MemberException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}

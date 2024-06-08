package com.example.community.member.exception;

import com.example.community.common.exception.BaseException;

public class MemberException extends BaseException {

	public MemberException(MemberExceptionType exceptionType) {
		super(exceptionType);
	}
}

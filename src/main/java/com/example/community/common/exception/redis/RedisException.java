package com.example.community.common.exception.redis;

import com.example.community.common.exception.BaseException;

public class RedisException extends BaseException {

	public RedisException(RedisExceptionType exceptionType) {
		super(exceptionType);
	}
}

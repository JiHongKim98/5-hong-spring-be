package com.example.community.common.presentation;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.example.community.common.config.CookieProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieHandler {

	private static final Long DELETE_COOKIE_MAX_AGE = 0L;
	private static final String DELETE_COOKIE_VALUE = "";

	private final CookieProperties properties;

	public ResponseCookie createCookie(String cookieKey, String cookieValue) {
		return ResponseCookie.from(cookieKey, cookieValue)
			.maxAge(properties.maxAge())
			.path(properties.path())
			.secure(properties.secure())
			.httpOnly(properties.httpOnly())
			.build();
	}

	public ResponseCookie deleteCookie(String cookieKey) {
		return ResponseCookie.from(cookieKey, DELETE_COOKIE_VALUE)
			.maxAge(DELETE_COOKIE_MAX_AGE)
			.path(properties.path())
			.secure(properties.secure())
			.httpOnly(properties.httpOnly())
			.build();
	}
}

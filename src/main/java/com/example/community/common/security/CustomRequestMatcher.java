package com.example.community.common.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class CustomRequestMatcher implements RequestMatcher {

	private final String pattern;
	private final HttpMethod method;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public CustomRequestMatcher(HttpMethod method, String pattern) {
		this.method = method;
		this.pattern = pattern;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		if (method != null && !method.name().equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		return pathMatcher.match(pattern, request.getServletPath());
	}
}

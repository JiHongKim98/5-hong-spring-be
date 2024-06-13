package com.example.community.common.security;

import static org.springframework.http.HttpMethod.*;

import java.util.List;

import org.springframework.http.HttpMethod;

public final class SecurityPaths {

	private SecurityPaths() {
	}

	public static final List<AuthPath> AUTHENTICATION_PATHS = List.of(
		// member
		new AuthPath(POST, "/members/logout"),
		new AuthPath(GET, "/members/me"),
		new AuthPath(PUT, "/members/nickname"),
		new AuthPath(PUT, "/members/password"),
		new AuthPath(DELETE, "/members"),

		// post
		new AuthPath(POST, "/posts/{id}"),
		new AuthPath(PUT, "/posts/{id}"),
		new AuthPath(DELETE, "/posts/{id}"),

		// comment
		new AuthPath(POST, "/comments/{id}"),
		new AuthPath(PUT, "/comments/{id}"),
		new AuthPath(DELETE, "/comments/{id}")
	);

	// Nested
	public record AuthPath(
		HttpMethod method,
		String path
	) {

		private static final String API_PREFIX = "/api/v1";

		@Override
		public String path() {
			return API_PREFIX + path;
		}
	}
}

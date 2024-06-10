package com.example.community.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TokenResponse(
	String accessToken,
	@JsonIgnore String refreshToken  // FIXME: 일단 임시로 refresh token 을 responseBody 에서 제외함 수정 필요
) {

	public static TokenResponse of(String accessToken, String refreshToken) {
		return new TokenResponse(accessToken, refreshToken);
	}
}

package com.example.community.member.application.dto;

import com.example.community.member.domain.Member;

public record SignupResponse(
	Long memberId
) {

	public static SignupResponse of(Member member) {
		return new SignupResponse(member.getId());
	}
}

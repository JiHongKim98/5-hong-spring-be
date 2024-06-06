package com.example.community.member.application.dto;

import com.example.community.member.domain.Member;

public record MemberInfoResponse(
	Long memberId,
	String email,
	String nickname,
	String profileImage
) {

	public static MemberInfoResponse of(Member member) {
		return new MemberInfoResponse(
			member.getId(),
			member.getEmail(),
			member.getNickname(),
			member.getProfileImage()
		);
	}
}

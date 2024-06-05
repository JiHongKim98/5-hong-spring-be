package com.example.community.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.member.application.dto.SignupRequest;
import com.example.community.member.application.dto.SignupResponse;
import com.example.community.member.domain.Member;
import com.example.community.member.domain.respository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberValidateService memberValidateService;

	@Transactional
	public SignupResponse signup(SignupRequest request) {
		memberValidateService.throwIfEmailDuplicate(request.email());
		memberValidateService.throwIfNicknameDuplicate(request.nickname());

		Member newMember = Member.builder()  // TODO: 비밀번호 해싱 or OAuth 전환
			.email(request.email())
			.nickname(request.nickname())
			.password(request.password())
			.profileImage(request.profileImage())
			.build();
		memberRepository.save(newMember);

		return SignupResponse.of(newMember);
	}
}

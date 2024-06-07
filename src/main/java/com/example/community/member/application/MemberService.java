package com.example.community.member.application;

import static com.example.community.member.exception.MemberExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.member.application.dto.MemberInfoResponse;
import com.example.community.member.application.dto.SignupRequest;
import com.example.community.member.application.dto.SignupResponse;
import com.example.community.member.application.dto.UpdateNicknameRequest;
import com.example.community.member.application.dto.UpdatePasswordRequest;
import com.example.community.member.domain.Member;
import com.example.community.member.domain.respository.MemberRepository;
import com.example.community.member.exception.MemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	// TODO: 공통 로직 리팩

	private final CryptService cryptService;
	private final MemberRepository memberRepository;
	private final MemberValidateService memberValidateService;

	@Transactional
	public SignupResponse signup(SignupRequest request) {
		memberValidateService.throwIfEmailDuplicate(request.email());
		memberValidateService.throwIfNicknameDuplicate(request.nickname());

		Member newMember = Member.builder()
			.email(request.email())
			.nickname(request.nickname())
			.password(cryptService.encode(request.password()))
			.profileImage(request.profileImage())
			.build();
		memberRepository.save(newMember);

		return SignupResponse.of(newMember);
	}

	public MemberInfoResponse getCurrentMemberInfo(Long memberId) {
		Member findMember = memberRepository.findByIdAndIsActiveTrue(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));
		return MemberInfoResponse.of(findMember);
	}

	@Transactional
	public void withdraw(Long memberId) {
		Member findMember = memberRepository.findByIdAndIsActiveTrue(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));

		findMember.disabledMember();
		memberRepository.save(findMember);
	}

	@Transactional
	public void updatePassword(Long memberId, UpdatePasswordRequest request) {
		Member findMember = memberRepository.findByIdAndIsActiveTrue(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));

		findMember.updatePassword(request.password());

		memberRepository.save(findMember);
	}

	@Transactional
	public void updateNickname(Long memberId, UpdateNicknameRequest request) {
		Member findMember = memberRepository.findByIdAndIsActiveTrue(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));

		findMember.updateNickname(request.nickname());

		memberRepository.save(findMember);
	}
}

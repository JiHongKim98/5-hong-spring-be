package com.example.community.member.application;

import static com.example.community.member.exception.MemberExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.member.domain.respository.MemberRepository;
import com.example.community.member.exception.MemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberValidateService {

	private final MemberRepository memberRepository;

	public void throwIfEmailDuplicate(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new MemberException(DUPLICATE_EMAIL);
		}
	}

	public void throwIfNicknameDuplicate(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new MemberException(DUPLICATE_NICKNAME);
		}
	}
}

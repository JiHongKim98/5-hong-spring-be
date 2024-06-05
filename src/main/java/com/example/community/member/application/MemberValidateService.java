package com.example.community.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.member.domain.respository.MemberRepository;

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
			throw new RuntimeException("409 중복된 이메일");
		}
	}

	public void throwIfNicknameDuplicate(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new RuntimeException("409 중복된 닉네임");
		}
	}
}

package com.example.community.auth.application;

import static com.example.community.auth.exception.AuthExceptionType.*;
import static com.example.community.member.exception.MemberExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.auth.application.dto.LoginRequest;
import com.example.community.auth.application.dto.LogoutRequest;
import com.example.community.auth.application.dto.ReissueRequest;
import com.example.community.auth.application.dto.TokenResponse;
import com.example.community.auth.domain.Token;
import com.example.community.auth.domain.repository.TokenRepository;
import com.example.community.auth.exception.AuthException;
import com.example.community.member.domain.Member;
import com.example.community.member.domain.respository.MemberRepository;
import com.example.community.member.exception.MemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {  // TODO: 공통 로직 리팩토링

	private final CryptService cryptService;
	private final TokenProvider tokenProvider;
	private final TokenExtractor tokenExtractor;
	private final TokenRepository tokenRepository;
	private final MemberRepository memberRepository;

	public TokenResponse login(LoginRequest request) {
		Member findMember = memberRepository.findByEmailAndIsActiveTrue(request.email())
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));

		cryptService.isMatchOrThrow(request.password(), findMember.getPassword());

		Token token = new Token(findMember.getId());
		tokenRepository.save(token);
		String accessToken = tokenProvider.generatedAccessToken(token.getMemberId());
		String refreshToken = tokenProvider.generatedRefreshToken(token.getTokenId());
		return TokenResponse.of(accessToken, refreshToken);
	}

	public TokenResponse reissueToken(ReissueRequest request) {
		String tokenId = tokenExtractor.extractRefreshToken(request.refreshToken());
		Token findToken = tokenRepository.findByTokenId(tokenId)
			.orElseThrow(() -> new AuthException(INVALID_TOKEN));

		tokenRepository.deleteByTokenId(tokenId);  // refresh rotation

		String accessToken = tokenProvider.generatedAccessToken(findToken.getMemberId());
		String refreshToken = tokenProvider.generatedRefreshToken(findToken.getTokenId());
		return TokenResponse.of(accessToken, refreshToken);
	}

	public void logout(Long memberId, LogoutRequest request) {
		String tokenId = tokenExtractor.extractRefreshToken(request.refreshToken());

		Token findToken = tokenRepository.findByTokenId(request.refreshToken())
			.orElseThrow(() -> new AuthException(INVALID_TOKEN));
		if (!findToken.isMatchMemberId(memberId)) {
			throw new AuthException(UN_MATCHED_AUTHORIZATION);
		}

		tokenRepository.deleteByTokenId(tokenId);
	}
}

package com.example.community.auth.infrastructure.security;

import static com.example.community.auth.exception.AuthExceptionType.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.community.auth.application.CryptService;
import com.example.community.auth.exception.AuthException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BCryptService implements CryptService {

	private final PasswordEncoder bCryptPasswordEncoder;

	public String encode(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public void isMatchOrThrow(String targetPassword, String encodedPassword) {
		boolean matchesResult = bCryptPasswordEncoder.matches(targetPassword, encodedPassword);
		if (!matchesResult) {
			throw new AuthException(UN_MATCHED_PASSWORD);
		}
	}
}

package com.example.community.auth.infrastructure.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.community.auth.application.CryptService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BCryptService implements CryptService {

	private final PasswordEncoder bCryptPasswordEncoder;

	public String encode(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public boolean isMatches(String targetPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(targetPassword, encodedPassword);
	}
}

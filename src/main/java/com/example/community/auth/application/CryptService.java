package com.example.community.auth.application;

public interface CryptService {

	String encode(String password);

	void isMatchOrThrow(String targetPassword, String encodedPassword);
}

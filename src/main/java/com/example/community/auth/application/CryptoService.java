package com.example.community.auth.application;

public interface CryptoService {

	String encode(String password);

	void isMatchOrThrow(String targetPassword, String encodedPassword);
}

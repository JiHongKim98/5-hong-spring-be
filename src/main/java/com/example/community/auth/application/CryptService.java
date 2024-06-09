package com.example.community.auth.application;

public interface CryptService {

	String encode(String password);

	boolean isMatches(String targetPassword, String encodedPassword);
}

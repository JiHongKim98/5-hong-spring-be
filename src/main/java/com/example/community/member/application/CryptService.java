package com.example.community.member.application;

public interface CryptService {

	String encode(String password);

	boolean isMatches(String targetPassword, String encodedPassword);
}

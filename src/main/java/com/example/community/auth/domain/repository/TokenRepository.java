package com.example.community.auth.domain.repository;

import java.util.Optional;

import com.example.community.auth.domain.Token;

// MySQL 이 아닌 InMemory 나 Redis 로 변경할 수 있어서 Data JPA 미사용
public interface TokenRepository {

	void save(Token token);

	void deleteByTokenId(String tokenId);

	Optional<Token> findByTokenId(String tokenId);
}

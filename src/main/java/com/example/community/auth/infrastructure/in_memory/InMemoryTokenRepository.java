package com.example.community.auth.infrastructure.in_memory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.example.community.auth.domain.Token;
import com.example.community.auth.domain.repository.TokenRepository;

// In-Memory Base (임시)
@Component
public class InMemoryTokenRepository implements TokenRepository {

	private static final ConcurrentHashMap<String, Token> memory = new ConcurrentHashMap<>();

	@Override
	public void save(Token token) {
		memory.put(token.getTokenId(), token);
	}

	@Override
	public void deleteByTokenId(String tokenId) {
		memory.remove(tokenId);
	}

	@Override
	public Optional<Token> findByTokenId(String tokenId) {
		return Optional.ofNullable(memory.get(tokenId));
	}
}

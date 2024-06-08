package com.example.community.auth.infrastructure.in_memory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.example.community.auth.domain.Token;
import com.example.community.auth.domain.repository.TokenRepository;

/**
 * @deprecated 테스용 인메모리 리포지토리입니다.
 * {@link com.example.community.auth.infrastructure.redis.RedisTokenRepository} 를 사용중
 */
@Deprecated(since = "2024-06-08")
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

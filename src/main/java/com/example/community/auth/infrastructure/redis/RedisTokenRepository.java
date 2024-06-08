package com.example.community.auth.infrastructure.redis;

import static com.example.community.common.exception.redis.RedisExceptionType.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.community.auth.domain.Token;
import com.example.community.auth.domain.repository.TokenRepository;
import com.example.community.common.exception.redis.RedisException;
import com.example.community.common.utils.CommunityStringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisTokenRepository implements TokenRepository {

	private static final Long TTL = 10_080L;  // FIXME: TTL 하드코딩 변경
	private static final String PREFIX = "token";
	private static final String SEPARATOR = ":";

	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void save(Token token) {
		redisTemplate.opsForValue().set(key(token.getTokenId()), serializeToken(token));
		redisTemplate.expire(key(token.getTokenId()), TTL, TimeUnit.MINUTES);
	}

	@Override
	public void deleteByTokenId(String tokenId) {
		redisTemplate.delete(key(tokenId));
	}

	@Override
	public Optional<Token> findByTokenId(String tokenId) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(key(tokenId)))
			.map(this::deserializeToken);
	}

	private String key(String tokenId) {
		return CommunityStringUtils.concatenateStrings(PREFIX, SEPARATOR, tokenId);
	}

	private String serializeToken(Token token) {
		try {
			return objectMapper.writeValueAsString(token);
		} catch (JsonProcessingException ex) {
			throw new RedisException(SERIALIZE_ERROR);
		}
	}

	private Token deserializeToken(String tokenJson) {
		try {
			return objectMapper.readValue(tokenJson, Token.class);
		} catch (JsonProcessingException ex) {
			throw new RedisException(DESERIALIZE_ERROR);
		}
	}
}

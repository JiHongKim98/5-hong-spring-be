package com.example.community.auth.infrastructure.jwt;

import static com.example.community.auth.exception.AuthExceptionType.*;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.community.auth.application.TokenExtractor;
import com.example.community.auth.exception.AuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtExtractor implements TokenExtractor {

	// TODO: Payload 관련 상수 따로 관리
	private static final String MEMBER_ID = "member_id";
	private static final String TOKEN_ID = "token_id";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String REFRESH_TOKEN = "refresh_token";

	private final JwtParser jwtParser;

	public JwtExtractor(
		@Value("${jwt.secret-key}") String secretKey
	) {
		SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.jwtParser = Jwts.parserBuilder()
			.setSigningKey(key)
			.build();
	}

	@Override
	public Long extractAccessToken(String token) {
		return extract(token, ACCESS_TOKEN, MEMBER_ID, Long.class);
	}

	@Override
	public String extractRefreshToken(String token) {
		return extract(token, REFRESH_TOKEN, TOKEN_ID, String.class);
	}

	private <T> T extract(String token, String expectedTokenType, String claimKey, Class<T> T) {
		Claims claims = parseClaim(token);
		String subject = claims.getSubject();

		if (subject.equals(expectedTokenType)) {
			return claims.get(claimKey, T);
		}
		throw new AuthException(INVALID_TOKEN_TYPE);
	}

	private Claims parseClaim(String token) {
		try {
			return jwtParser.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException ex) {
			throw new AuthException(EXPIRED_TOKEN);
		} catch (Exception ex) {
			throw new AuthException(INVALID_TOKEN);
		}
	}
}

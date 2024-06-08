package com.example.community.auth.presentation.support;

import static com.example.community.auth.exception.AuthExceptionType.*;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.community.auth.application.TokenExtractor;
import com.example.community.auth.exception.AuthException;
import com.example.community.common.annotation.LoginRequired;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private final AuthContext authContext;
	private final TokenExtractor tokenExtractor;

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		if (isLoginRequired(handler)) {
			String token = getTokenOrThrow(request);
			Long memberId = tokenExtractor.extractAccessToken(token);
			authContext.setContext(memberId);
		}
		return true;
	}

	// @LoginRequired 어노테이션 검증
	private boolean isLoginRequired(Object handler) {
		if (handler instanceof HandlerMethod handlerMethod) {
			LoginRequired annotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
			return Objects.nonNull(annotation);
		}
		return false;
	}

	private String getTokenOrThrow(HttpServletRequest request) {
		return AuthHeaderExtractor.extract(request)
			.orElseThrow(() -> new AuthException(UNAUTHORIZED));
	}
}

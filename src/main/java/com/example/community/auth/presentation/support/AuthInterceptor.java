package com.example.community.auth.presentation.support;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.community.auth.application.TokenExtractor;
import com.example.community.auth.exception.AuthException;
import com.example.community.auth.exception.AuthExceptionType;
import com.example.community.common.annotation.LoginRequired;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	// TODO: 리팩토링

	private final AuthContext authContext;
	private final TokenExtractor tokenExtractor;

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		LoginRequired annotation = handlerMethod.getMethodAnnotation(LoginRequired.class);
		if (Objects.isNull(annotation)) {
			return true;
		}

		String token = AuthHeaderExtractor.extract(request)
			.orElseThrow(() -> new AuthException(AuthExceptionType.UNAUTHORIZED));
		Long memberId = tokenExtractor.extractAccessToken(token);
		authContext.setContext(memberId);
		return true;
	}
}

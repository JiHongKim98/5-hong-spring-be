package com.example.community.common.config;

import java.util.List;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.community.auth.presentation.support.AuthArgumentResolver;
import com.example.community.auth.presentation.support.AuthInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * @deprecated interceptor 기반 -> Security 를 사용한 인증으로 변경
 * {@link com.example.community.common.security} 패키지 참고
 */
// @Configuration  // deprecated
@Deprecated(since = "2024-06-13")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;
	private final AuthArgumentResolver authArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor);
	}
}

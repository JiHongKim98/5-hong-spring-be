package com.example.community.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.community.common.logging.MdcLoggingFilter;
import com.example.community.common.logging.RequestLoggingFilter;
import com.example.community.common.logging.query.QueryCountInterceptor;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoggingConfig implements WebMvcConfigurer {

	private final QueryCountInterceptor queryCountInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(queryCountInterceptor)
			.addPathPatterns("/**")
			.order(0);
	}

	@Bean
	public FilterRegistrationBean<Filter> mdcLoggingFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

		registration.setFilter(new MdcLoggingFilter());
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registration.addUrlPatterns("/*");

		return registration;
	}

	@Bean
	public FilterRegistrationBean<Filter> requestLoggingFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

		registration.setFilter(new RequestLoggingFilter());
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		registration.addUrlPatterns("/*");

		return registration;
	}
}

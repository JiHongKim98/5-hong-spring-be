package com.example.community.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.community.common.logging.MdcLoggingFilter;
import com.example.community.common.logging.RequestLoggingFilter;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoggingConfig implements WebMvcConfigurer {

	// 필터 순서 (MDC) -> (REQUEST CACHE) -> (THE OTHERS...)

	@Bean
	public FilterRegistrationBean<Filter> filter1() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

		registration.setFilter(new MdcLoggingFilter());
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registration.addUrlPatterns("/*");

		return registration;
	}

	@Bean
	public FilterRegistrationBean<Filter> filterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

		registration.setFilter(new RequestLoggingFilter());
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		registration.addUrlPatterns("/*");

		return registration;
	}
}

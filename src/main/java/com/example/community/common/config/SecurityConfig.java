package com.example.community.common.config;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import com.example.community.common.security.CustomRequestMatcher;
import com.example.community.common.security.SecurityPaths;
import com.example.community.common.security.handler.CustomAccessDeniedHandler;
import com.example.community.common.security.handler.CustomAuthenticationEntryPoint;
import com.example.community.common.security.jwt.JwtAuthenticationFilter;
import com.example.community.common.security.jwt.JwtAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		OrRequestMatcher requestMatcher = new OrRequestMatcher(
			SecurityPaths.AUTHENTICATION_PATHS.stream()
				.map(path -> new CustomRequestMatcher(path.method(), path.path()))
				.collect(Collectors.toList())
		);

		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(requestMatcher);
		filter.setAuthenticationManager(authenticationManager);
		filter.setAuthenticationFailureHandler(
			new AuthenticationEntryPointFailureHandler(authenticationEntryPoint)
		);
		return filter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity http,
		AuthenticationManager authenticationManager
	) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling(handler -> handler
				.accessDeniedHandler(accessDeniedHandler)
			)
			.addFilterBefore(
				jwtAuthenticationFilter(authenticationManager),
				UsernamePasswordAuthenticationFilter.class
			)
			.authenticationProvider(jwtAuthenticationProvider);

		return http.build();
	}
}

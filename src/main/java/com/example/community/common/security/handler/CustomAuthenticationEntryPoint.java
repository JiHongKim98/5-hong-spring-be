package com.example.community.common.security.handler;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(UTF_8.name());

		// response body 생성
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("code", "UNAUTHORIZED");
		responseBody.put("message", failed.getMessage());

		// JSON 응답으로 작성
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(
			objectMapper.writeValueAsString(responseBody)
		);
	}
}

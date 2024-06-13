package com.example.community.common.security.handler;

import static java.nio.charset.StandardCharsets.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(UTF_8.name());

		// response body 생성
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("code", "FORBIDDEN");
		responseBody.put("message", accessDeniedException.getMessage());

		// JSON 응답으로 작성
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(
			objectMapper.writeValueAsString(responseBody)
		);
	}
}

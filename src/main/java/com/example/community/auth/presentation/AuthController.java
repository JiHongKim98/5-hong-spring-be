package com.example.community.auth.presentation;

import static org.springframework.http.HttpHeaders.*;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.auth.application.AuthService;
import com.example.community.auth.application.dto.LoginRequest;
import com.example.community.auth.application.dto.TokenResponse;
import com.example.community.common.presentation.CookieHandler;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthController {

	private static final String COOKIE_REFRESH_TOKEN = "refresh_token";

	private final AuthService authService;
	private final CookieHandler cookieHandler;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(
		@RequestBody @Valid LoginRequest loginRequest,
		HttpServletResponse response
	) {
		TokenResponse tokenPair = authService.login(loginRequest);
		ResponseCookie cookie = cookieHandler.createCookie(COOKIE_REFRESH_TOKEN, tokenPair.refreshToken());
		response.addHeader(SET_COOKIE, cookie.toString());
		return ResponseEntity.ok(tokenPair);
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenResponse> reissue(
		@CookieValue(COOKIE_REFRESH_TOKEN) String refreshToken,
		HttpServletResponse response
	) {
		TokenResponse tokenPair = authService.reissueToken(refreshToken);
		ResponseCookie cookie = cookieHandler.createCookie(COOKIE_REFRESH_TOKEN, tokenPair.refreshToken());
		response.addHeader(SET_COOKIE, cookie.toString());
		return ResponseEntity.ok(tokenPair);
	}

	// @LoginRequired
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
		// @Auth Long memberId,
		@AuthenticationPrincipal Long memberId,
		@CookieValue(COOKIE_REFRESH_TOKEN) String refreshToken,
		HttpServletResponse response
	) {
		authService.logout(memberId, refreshToken);
		ResponseCookie cookie = cookieHandler.deleteCookie(COOKIE_REFRESH_TOKEN);
		response.addHeader(SET_COOKIE, cookie.toString());
		return ResponseEntity.noContent().build();
	}
}

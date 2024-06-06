package com.example.community.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.auth.application.AuthService;
import com.example.community.auth.application.dto.LoginRequest;
import com.example.community.auth.application.dto.LogoutRequest;
import com.example.community.auth.application.dto.ReissueRequest;
import com.example.community.auth.application.dto.TokenResponse;
import com.example.community.common.annotation.Auth;
import com.example.community.common.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthController {

	// TODO: refresh_token -> COOKIE

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(
		@RequestBody LoginRequest loginRequest
	) {
		return ResponseEntity.ok(authService.login(loginRequest));
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenResponse> reissue(
		@RequestBody ReissueRequest reissueRequest
	) {
		return ResponseEntity.ok(authService.reissueToken(reissueRequest));
	}

	@LoginRequired
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
		@Auth Long memberId,
		@RequestBody LogoutRequest logoutRequest
	) {
		authService.logout(memberId, logoutRequest);
		return ResponseEntity.noContent().build();
	}
}

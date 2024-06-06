package com.example.community.member.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.common.annotation.Auth;
import com.example.community.common.annotation.LoginRequired;
import com.example.community.member.application.MemberService;
import com.example.community.member.application.dto.MemberInfoResponse;
import com.example.community.member.application.dto.SignupRequest;
import com.example.community.member.application.dto.SignupResponse;
import com.example.community.member.application.dto.UpdateNicknameRequest;
import com.example.community.member.application.dto.UpdatePasswordRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(
		@RequestBody SignupRequest signupRequest
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(memberService.signup(signupRequest));
	}

	@LoginRequired
	@GetMapping("/me")
	public ResponseEntity<MemberInfoResponse> me(
		@Auth Long memberId
	) {
		return ResponseEntity.ok(memberService.getCurrentMemberInfo(memberId));
	}

	@LoginRequired
	@PutMapping("/nickname")
	public ResponseEntity<Void> nicknameValidate(
		@Auth Long memberId,
		@RequestBody UpdateNicknameRequest request
	) {
		memberService.updateNickname(memberId, request);
		return ResponseEntity.ok().build();
	}

	@LoginRequired
	@PutMapping("/password")
	public ResponseEntity<Void> passwordValidate(
		@Auth Long memberId,
		@RequestBody UpdatePasswordRequest request
	) {
		memberService.updatePassword(memberId, request);
		return ResponseEntity.ok().build();
	}

	@LoginRequired
	@DeleteMapping
	public ResponseEntity<Void> withdraw(
		@Auth Long memberId
	) {
		memberService.withdraw(memberId);
		return ResponseEntity.ok().build();
	}
}

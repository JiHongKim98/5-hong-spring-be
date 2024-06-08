package com.example.community.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.member.application.MemberValidateService;
import com.example.community.member.application.dto.EmailValidateRequest;
import com.example.community.member.application.dto.NicknameValidateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberValidateController {

	private final MemberValidateService memberValidateService;

	@PostMapping("/email")
	public ResponseEntity<Void> emailValidate(
		@RequestBody @Valid EmailValidateRequest request
	) {
		memberValidateService.throwIfEmailDuplicate(request.email());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/nickname")
	public ResponseEntity<Void> nicknameValidate(
		@RequestBody @Valid NicknameValidateRequest request
	) {
		memberValidateService.throwIfNicknameDuplicate(request.nickname());
		return ResponseEntity.ok().build();
	}
}

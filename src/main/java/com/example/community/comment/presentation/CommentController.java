package com.example.community.comment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.comment.application.CommentService;
import com.example.community.comment.application.dto.CreateCommentRequest;
import com.example.community.comment.application.dto.CreateCommentResponse;
import com.example.community.comment.application.dto.PagedCommentResponse;
import com.example.community.comment.application.dto.UpdateCommentRequest;
import com.example.community.common.annotation.Auth;
import com.example.community.common.annotation.LoginRequired;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping
	public ResponseEntity<PagedCommentResponse> getCommentBySlice(
		@RequestParam("postId") Long postId,
		@RequestParam(value = "cursor", required = false) Long cursor
	) {
		return ResponseEntity.ok(commentService.getCommentBySlice(cursor, postId));
	}

	@LoginRequired
	@PostMapping
	public ResponseEntity<CreateCommentResponse> createComment(
		@Auth Long memberId,
		@RequestBody @Valid CreateCommentRequest request
	) {
		return ResponseEntity.ok(commentService.createComment(memberId, request));
	}

	@LoginRequired
	@PutMapping("/{commentId}")
	public ResponseEntity<Void> updateComment(
		@Auth Long memberId,
		@PathVariable("commentId") Long commentId,
		@RequestBody @Valid UpdateCommentRequest request
	) {
		commentService.updateComment(memberId, commentId, request);
		return ResponseEntity.ok().build();
	}

	@LoginRequired
	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(
		@Auth Long memberId,
		@PathVariable("commentId") Long commentId
	) {
		commentService.deleteComment(memberId, commentId);
		return ResponseEntity.ok().build();
	}
}

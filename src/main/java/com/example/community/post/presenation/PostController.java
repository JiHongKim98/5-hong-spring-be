package com.example.community.post.presenation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.post.application.PostService;
import com.example.community.post.application.dto.CreatePostRequest;
import com.example.community.post.application.dto.CreatePostResponse;
import com.example.community.post.application.dto.PagedPostResponse;
import com.example.community.post.application.dto.PostDetailResponse;
import com.example.community.post.application.dto.UpdatePostRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping
	public ResponseEntity<PagedPostResponse> getPostBySlice(
		@RequestParam(value = "cursor", required = false) Long cursor
	) {
		return ResponseEntity.ok(postService.getPostBySlice(cursor));
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDetailResponse> getPostInfo(
		@PathVariable("postId") Long postId
	) {
		return ResponseEntity.ok(postService.getPostInfo(postId));
	}

	// @LoginRequired
	@PostMapping
	public ResponseEntity<CreatePostResponse> createPost(
		// @Auth Long memberId,
		@AuthenticationPrincipal Long memberId,
		@RequestBody @Valid CreatePostRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(postService.createPost(memberId, request));
	}

	// @LoginRequired
	@PutMapping("/{postId}")
	public ResponseEntity<Void> updatePostInfo(
		// @Auth Long memberId,
		@AuthenticationPrincipal Long memberId,
		@PathVariable("postId") Long postId,
		@RequestBody @Valid UpdatePostRequest request
	) {
		postService.updatePost(memberId, postId, request);
		return ResponseEntity.ok().build();
	}

	// @LoginRequired
	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> disabledPost(
		// @Auth Long memberId,
		@AuthenticationPrincipal Long memberId,
		@PathVariable("postId") Long postId
	) {
		postService.deletePost(memberId, postId);
		return ResponseEntity.noContent().build();
	}
}

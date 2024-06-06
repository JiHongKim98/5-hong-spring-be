package com.example.community.post.application;

import static com.example.community.member.exception.MemberExceptionType.*;
import static com.example.community.post.exception.PostExceptionType.*;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.member.domain.Member;
import com.example.community.member.domain.respository.MemberRepository;
import com.example.community.member.exception.MemberException;
import com.example.community.post.application.dto.CreatePostRequest;
import com.example.community.post.application.dto.CreatePostResponse;
import com.example.community.post.application.dto.PagedPostResponse;
import com.example.community.post.application.dto.PostDetailResponse;
import com.example.community.post.application.dto.UpdatePostRequest;
import com.example.community.post.domain.Post;
import com.example.community.post.domain.repository.PostCustomRepository;
import com.example.community.post.domain.repository.PostRepository;
import com.example.community.post.exception.PostException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	// TODO: 회원 관련은 MemberService 주입받아 사용하고, 회원 도메인 Facade 패턴 도입
	// TODO: 게시글 도메인 Facade 패턴 도입

	private final PostRepository postRepository;
	private final PostCustomRepository postCustomRepository;
	private final MemberRepository memberRepository;

	public PagedPostResponse getPostBySlice(Long cursor) {
		Slice<PostDetailResponse> slice = postCustomRepository.findAllByCursorOrderByCreatedAtDesc(cursor);
		return PagedPostResponse.of(slice);
	}

	@Transactional
	public PostDetailResponse getPostInfo(Long postId) {
		Post findPost = postRepository.findByIdAndIsVisibleTrue(postId)
			.orElseThrow(() -> new PostException(NOT_EXIST_POST));

		findPost.increaseHitCount();
		postRepository.save(findPost);

		return PostDetailResponse.of(findPost);
	}

	@Transactional
	public CreatePostResponse createPost(Long memberId, CreatePostRequest request) {
		Member findMember = memberRepository.findByIdAndIsActiveTrue(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));

		Post newPost = Post.builder()
			.member(findMember)
			.title(request.title())
			.contents(request.contents())
			.thumbnail(request.thumbnail())
			.build();

		postRepository.save(newPost);

		return CreatePostResponse.of(newPost);
	}

	@Transactional
	public void updatePost(Long memberId, Long postId, UpdatePostRequest request) {
		Post findPost = postRepository.findByIdAndIsVisibleTrue(postId)
			.orElseThrow(() -> new PostException(NOT_EXIST_POST));

		findPost.isOwnerOrThrow(memberId);
		findPost.updateTitle(request.title());
		findPost.updateContents(request.contents());
		findPost.updateThumbnail(request.thumbnail());

		postRepository.save(findPost);
	}

	@Transactional
	public void deletePost(Long memberId, Long postId) {
		Post findPost = postRepository.findByIdAndIsVisibleTrue(postId)
			.orElseThrow(() -> new PostException(NOT_EXIST_POST));

		findPost.isOwnerOrThrow(memberId);

		postRepository.delete(findPost);
	}

	// external service - 댓글에서 씀
	@Transactional
	public void increaseCommentCount(Long postId) {
		Post findPost = postRepository.findByIdAndIsVisibleTrue(postId)
			.orElseThrow(() -> new PostException(NOT_EXIST_POST));

		findPost.increaseCommentCount();

		postRepository.save(findPost);
	}
}

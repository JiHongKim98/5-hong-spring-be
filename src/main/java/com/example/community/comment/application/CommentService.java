package com.example.community.comment.application;

import static com.example.community.comment.exception.CommentExceptionType.*;
import static com.example.community.member.exception.MemberExceptionType.*;
import static com.example.community.post.exception.PostExceptionType.*;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.comment.application.dto.CommentDetailResponse;
import com.example.community.comment.application.dto.CreateCommentRequest;
import com.example.community.comment.application.dto.CreateCommentResponse;
import com.example.community.comment.application.dto.PagedCommentResponse;
import com.example.community.comment.application.dto.UpdateCommentRequest;
import com.example.community.comment.domain.Comment;
import com.example.community.comment.domain.respository.CommentCustomRepository;
import com.example.community.comment.domain.respository.CommentRepository;
import com.example.community.comment.exception.CommentException;
import com.example.community.member.domain.Member;
import com.example.community.member.domain.respository.MemberRepository;
import com.example.community.member.exception.MemberException;
import com.example.community.post.application.PostService;
import com.example.community.post.domain.Post;
import com.example.community.post.domain.repository.PostRepository;
import com.example.community.post.exception.PostException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	// TODO: 댓글, 게시글 Facade 및 리팩토링

	// TODO: dependency 가 너무 많음
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;
	private final CommentCustomRepository commentCustomRepository;
	private final PostService postService;

	public PagedCommentResponse getCommentBySlice(Long cursor, Long postId) {
		Slice<CommentDetailResponse> slice = commentCustomRepository.findAllByCursorAndPostIdOrderByCreatedAtDesc(
			cursor, postId
		);
		return PagedCommentResponse.of(slice);
	}

	@Transactional
	public CreateCommentResponse createComment(Long memberId, CreateCommentRequest request) {
		Member member = memberRepository.findByIdAndIsActiveTrue(memberId)
			.orElseThrow(() -> new MemberException(NOT_EXIST_MEMBER));

		Post post = postRepository.findByIdAndIsVisibleTrue(request.postId())
			.orElseThrow(() -> new PostException(NOT_EXIST_POST));

		Comment newComment = Comment.builder()
			.member(member)
			.post(post)
			.contents(request.contents())
			.build();
		commentRepository.save(newComment);

		postService.increaseCommentCount(request.postId());

		return CreateCommentResponse.of(newComment);
	}

	@Transactional
	public void updateComment(Long memberId, Long commentId, UpdateCommentRequest request) {
		Comment findComment = commentRepository.findByIdAndIsVisibleTrue(commentId)
			.orElseThrow(() -> new CommentException(NOT_EXIST_COMMENT));

		findComment.isOwnerOrThrow(memberId);
		findComment.updateContents(request.contents());

		commentRepository.save(findComment);
	}

	@Transactional
	public void deleteComment(Long memberId, Long commentId) {
		Comment findComment = commentRepository.findByIdAndIsVisibleTrue(commentId)
			.orElseThrow(() -> new CommentException(NOT_EXIST_COMMENT));

		findComment.isOwnerOrThrow(memberId);

		commentRepository.delete(findComment);
	}
}

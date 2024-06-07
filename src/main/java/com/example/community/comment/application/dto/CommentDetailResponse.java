package com.example.community.comment.application.dto;

import java.time.LocalDateTime;

import com.example.community.comment.domain.Comment;
import com.example.community.member.domain.Member;

public record CommentDetailResponse(
	Long commentId,
	Long postId,
	String contents,
	LocalDateTime createdAt,
	Owner owner
) {

	public static CommentDetailResponse of(Comment comment) {
		return new CommentDetailResponse(
			comment.getId(),
			comment.getPost().getId(),
			comment.getContents(),
			comment.getCreatedAt(),
			Owner.of(comment.getMember())
		);
	}

	// Nested
	public record Owner(
		Long memberId,
		String nickname,
		String profileImage
	) {

		public static Owner of(Member member) {
			return new Owner(
				member.getId(),
				member.getNickname(),
				member.getProfileImage()
			);
		}
	}
}

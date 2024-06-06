package com.example.community.post.application.dto;

import java.time.LocalDateTime;

import com.example.community.member.domain.Member;
import com.example.community.post.domain.Post;

public record PostDetailResponse(
	Long postId,
	String title,
	String contents,
	String thumbnail,
	LocalDateTime createdAt,
	int hitCount,
	int likeCount,
	int commentCount,
	Owner owner
) {

	public static PostDetailResponse of(Post post) {
		return new PostDetailResponse(
			post.getId(),
			post.getTitle(),
			post.getContents(),
			post.getThumbnail(),
			post.getCreatedAt(),
			post.getHitCount(),
			post.getLikeCount(),
			post.getCommentCount(),
			Owner.of(post.getMember())
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


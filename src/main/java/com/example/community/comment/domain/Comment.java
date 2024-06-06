package com.example.community.comment.domain;

import static com.example.community.comment.exception.CommentExceptionType.*;

import org.hibernate.annotations.SQLDelete;

import com.example.community.comment.exception.CommentException;
import com.example.community.common.domain.BaseTimeEntity;
import com.example.community.member.domain.Member;
import com.example.community.post.domain.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@SQLDelete(  // JPA DELETE 는 SOFT DELETE 로 설정
	sql = "UPDATE comment SET is_visible = FALSE WHERE comment_id = ?"
)
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(columnDefinition = "LONGTEXT")
	private String contents;

	@Column(name = "is_visible")
	private boolean isVisible;

	@Builder
	public Comment(Member member, Post post, String contents) {
		this.member = member;
		this.post = post;
		this.contents = contents;
		isVisible = true;
	}

	public boolean checkIsVisible() {
		return isVisible;
	}

	public void isOwnerOrThrow(Long memberId) {
		if (member.getId().equals(memberId)) {
			return;
		}
		throw new CommentException(NOT_OWNER);
	}

	public void updateContents(String contents) {
		this.contents = contents;
	}
}

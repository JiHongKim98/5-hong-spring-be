package com.example.community.post.domain;

import static com.example.community.post.exception.PostExceptionType.*;

import org.hibernate.annotations.SQLDelete;

import com.example.community.common.domain.BaseTimeEntity;
import com.example.community.member.domain.Member;
import com.example.community.post.exception.PostException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@SQLDelete(  // JPA DELETE 는 SOFT DELETE 로 설정
	sql = "UPDATE post SET is_visible = false WHERE post_id = ?"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private Member member;

	private String title;

	@Column(columnDefinition = "LONGTEXT")
	private String contents;

	private String thumbnail;

	@Column(name = "like_count")
	private int likeCount;

	@Column(name = "comment_count")
	private int commentCount;

	@Column(name = "hit_count")
	private int hitCount;

	@Column(name = "is_visible")
	private boolean isVisible;

	@Builder
	public Post(Member member, String title, String contents, String thumbnail) {
		this.member = member;
		this.title = title;
		this.contents = contents;
		this.thumbnail = thumbnail;
		this.likeCount = 0;
		this.commentCount = 0;
		this.hitCount = 0;
		this.isVisible = true;
	}

	public Long findMemberId() {
		return member.getId();
	}

	public void isOwnerOrThrow(Long memberId) {
		if (member.getId().equals(memberId)) {
			return;
		}
		throw new PostException(NOT_OWNER);
	}

	public void increaseHitCount() {
		this.hitCount++;
	}

	public void increaseCommentCount() {
		this.commentCount++;
	}

	public void decreaseCommentCount() {
		this.commentCount--;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateContents(String contents) {
		this.contents = contents;
	}

	public void updateThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}

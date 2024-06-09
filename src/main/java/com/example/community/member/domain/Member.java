package com.example.community.member.domain;

import org.hibernate.annotations.SQLDelete;

import com.example.community.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "member")
@SQLDelete(  // JPA DELETE 는 SOFT DELETE 로 설정
	sql = "UPDATE member SET is_active = FALSE WHERE member_id = ?"
)
// @SQLRestriction("is_active = TRUE")  // hibernate 6.3 부터 `@Where` deprecated -> `@SQLRestriction`
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Setter  // FIXME: JPA 로 변경시 삭제
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String nickname;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "is_active")
	private boolean isActive;

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileImage(String imageUrl) {
		profileImage = imageUrl;
	}

	public void disabledMember() {
		isActive = false;
	}

	public void enabledMember() {
		isActive = true;
	}

	// @Builder
	public Member(String email, String password, String nickname, String profileImage) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.isActive = true;
	}

	public Member(Long id, String email, String password, String nickname, String profileImage, boolean isActive) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.isActive = isActive;
	}

	// Nested
	public static class MemberBuilder {
		private Long id;
		private String email;
		private String password;
		private String nickname;
		private String profileImage;
		private boolean isActive = true;

		public MemberBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public MemberBuilder email(String email) {
			this.email = email;
			return this;
		}

		public MemberBuilder password(String password) {
			this.password = password;
			return this;
		}

		public MemberBuilder nickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public MemberBuilder profileImage(String profileImage) {
			this.profileImage = profileImage;
			return this;
		}

		public MemberBuilder isActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}

		public Member build() {
			if (id != null) {
				return new Member(id, email, password, nickname, profileImage, isActive);
			}
			return new Member(email, password, nickname, profileImage);
		}
	}

	public static MemberBuilder builder() {
		return new MemberBuilder();
	}
}

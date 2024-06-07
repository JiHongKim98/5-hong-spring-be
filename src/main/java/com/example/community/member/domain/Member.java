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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: @SQLRestriction 을 제외 하려면 네이티브 쿼리 작성해야함 -> MySQL boolean 타입으로 캐스팅 불가 해결해야함
@Getter
@Entity
@Table(name = "member")
@SQLDelete(  // JPA DELETE 는 SOFT DELETE 로 설정
	sql = "UPDATE member SET is_active = FALSE WHERE member_id = ?"
)
// @SQLRestriction("is_active = TRUE")  // hibernate 6.3 부터 `@Where` deprecated -> `@SQLRestriction`
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

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

	public void disabledMember() {
		isActive = false;
	}

	public void enabledMember() {
		isActive = true;
	}

	@Builder
	public Member(String email, String password, String nickname, String profileImage) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.isActive = true;
	}
}

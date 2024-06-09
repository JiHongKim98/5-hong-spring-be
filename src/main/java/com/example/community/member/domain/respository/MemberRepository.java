package com.example.community.member.domain.respository;

import java.util.Optional;

import com.example.community.member.domain.Member;

// FIXME: 승인 후 DATA-JPA 로 변경
public interface MemberRepository {

	Member save(Member member);

	void delete(Member member);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	Optional<Member> findByIdAndIsActiveTrue(Long memberId);

	Optional<Member> findByEmailAndIsActiveTrue(String username);
}

package com.example.community.member.domain.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.community.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	Optional<Member> findByIdAndIsActiveTrue(Long memberId);

	Optional<Member> findByEmailAndIsActiveTrue(String username);
}

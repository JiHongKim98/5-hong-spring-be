package com.example.community.post.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.community.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// TODO: N+1

	Optional<Post> findByIdAndIsVisibleTrue(Long postId);
}

package com.example.community.comment.domain.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.community.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByIdAndIsVisibleTrue(Long commentId);
}

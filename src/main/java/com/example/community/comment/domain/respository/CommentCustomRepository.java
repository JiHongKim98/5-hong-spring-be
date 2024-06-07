package com.example.community.comment.domain.respository;

import org.springframework.data.domain.Slice;

import com.example.community.comment.application.dto.CommentDetailResponse;

public interface CommentCustomRepository {

	Slice<CommentDetailResponse> findAllByCursorAndPostIdOrderByCreatedAtDesc(Long cursor, Long postId);
}

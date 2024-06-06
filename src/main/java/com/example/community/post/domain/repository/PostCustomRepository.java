package com.example.community.post.domain.repository;

import org.springframework.data.domain.Slice;

import com.example.community.post.application.dto.PostDetailResponse;

public interface PostCustomRepository {

	Slice<PostDetailResponse> findAllByCursorOrderByCreatedAtDesc(Long cursor);
}

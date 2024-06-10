package com.example.community.post.infrastructure.querydsl;

import static com.example.community.member.domain.QMember.*;
import static com.example.community.post.domain.QPost.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.example.community.post.application.dto.PostDetailResponse;
import com.example.community.post.domain.Post;
import com.example.community.post.domain.repository.PostCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueryDslPostRepository implements PostCustomRepository {

	private static final Long maxViewSize = 5L;  // 최대로 보여줄 게시글 수
	private static final Long sliceSize = maxViewSize + 1L;

	private final JPAQueryFactory queryFactory;

	@Override
	public Slice<PostDetailResponse> findAllByCursorOrderByCreatedAtDesc(Long cursor) {
		List<Post> fetch = queryFactory.selectFrom(post)
			.join(post.member, member)
			.fetchJoin()
			.where(
				post.isVisible.isTrue(),
				postIdLt(cursor)  // 동적 쿼리
			)
			.orderBy(post.createdAt.desc())
			.limit(sliceSize)
			.fetch();

		boolean hasNext = fetch.size() > maxViewSize;
		if (hasNext) {
			fetch.remove(fetch.size() - 1);
		}

		List<PostDetailResponse> responses = fetch.stream()
			.map(PostDetailResponse::of)
			.collect(Collectors.toList());

		return new SliceImpl<>(responses, PageRequest.of(0, maxViewSize.intValue()), hasNext);
	}

	private BooleanExpression postIdLt(Long cursor) {
		return Objects.nonNull(cursor) ? post.id.lt(cursor) : null;
	}
}

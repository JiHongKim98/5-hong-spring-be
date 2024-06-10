package com.example.community.comment.infrastructure.querydsl;

import static com.example.community.comment.domain.QComment.*;
import static com.example.community.member.domain.QMember.*;
import static com.example.community.post.domain.QPost.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.example.community.comment.application.dto.CommentDetailResponse;
import com.example.community.comment.domain.Comment;
import com.example.community.comment.domain.respository.CommentCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueryDslCommentRepository implements CommentCustomRepository {

	private static final Long maxViewSize = 10L;  // 최대로 보여줄 댓글 수
	private static final Long sliceSize = maxViewSize + 1L;

	private final JPAQueryFactory queryFactory;

	@Override
	public Slice<CommentDetailResponse> findAllByCursorAndPostIdOrderByCreatedAtDesc(Long cursor, Long postId) {
		List<Comment> fetch = queryFactory.selectFrom(comment)
			.join(comment.member, member)
			.fetchJoin()
			.join(comment.post, post)
			.fetchJoin()
			.where(
				comment.isVisible.isTrue(),
				comment.post.id.eq(postId),
				commentIdLt(cursor)  // 동적 쿼리
			)
			.orderBy(comment.createdAt.desc())
			.limit(sliceSize)
			.fetch();

		boolean hasNext = fetch.size() > maxViewSize;
		if (hasNext) {
			fetch.remove(fetch.size() - 1);
		}

		List<CommentDetailResponse> responses = fetch.stream()
			.map(CommentDetailResponse::of)
			.collect(Collectors.toList());

		return new SliceImpl<>(responses, PageRequest.of(0, maxViewSize.intValue()), hasNext);
	}

	private BooleanExpression commentIdLt(Long cursor) {
		return Objects.nonNull(cursor) ? comment.id.lt(cursor) : null;
	}
}

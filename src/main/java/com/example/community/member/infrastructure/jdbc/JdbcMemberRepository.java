package com.example.community.member.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.community.member.domain.Member;
import com.example.community.member.domain.respository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcMemberRepository implements MemberRepository {

	private static final String MEMBER_TABLE_NAME = "member";
	private static final String MEMBER_TABLE_PRIMARY_KEY = "member_id";

	private final SimpleJdbcInsert jdbcInsert;
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcMemberRepository(DataSource dataSource) {
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName(MEMBER_TABLE_NAME)
			.usingGeneratedKeyColumns(MEMBER_TABLE_PRIMARY_KEY);
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * JPA 와의 호환성을 위해 `save()` 메서드에서 엔티티 클래스의 id 값의 유무로 insert 및 update 를 진행하도록 구현
	 * @param member 엔티티 클래스
	 * @return Member
	 */
	@Override
	public Member save(Member member) {
		if (member.getId() == null) {
			return insert(member);  // insert 문
		}
		return update(member);  // update 문
	}

	private Member insert(Member member) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("email", member.getEmail());
		param.addValue("password", member.getPassword());
		param.addValue("nickname", member.getNickname());
		param.addValue("profile_image", member.getProfileImage());
		param.addValue("is_active", member.isActive());
		param.addValue("createdAt", member.getCreatedAt());
		param.addValue("updatedAt", member.getUpdatedAt());

		Number key = jdbcInsert.executeAndReturnKey(param);
		member.setId(key.longValue()); // PK 부여 (id :: member_id)
		return member;
	}

	// 변경 감지는 고려하지 않음
	private Member update(Member member) {
		String sql = """
			    UPDATE member
			    SET password = :password,
			        nickname = :nickname,
			        profile_image = :profileImage,
			    WHERE member_id = :id
			""";
		SqlParameterSource param = new BeanPropertySqlParameterSource(member);
		jdbcTemplate.update(sql, param);
		return member;
	}

	@Override
	public void delete(Member member) {
		String sql = """
			UPDATE member
			SET is_active = FALSE
			WHERE member_id = :id
			""";
		SqlParameterSource param = new BeanPropertySqlParameterSource(member);
		jdbcTemplate.update(sql, param);
	}

	@Override
	public boolean existsByEmail(String email) {
		String sql = """
			    SELECT COUNT(m.member_id)
			    FROM member m
			    WHERE email = :email
			""";
		SqlParameterSource param = new MapSqlParameterSource("email", email);
		Integer count = jdbcTemplate.queryForObject(sql, param, Integer.class);
		return count != null && count > 0;
	}

	@Override
	public boolean existsByNickname(String nickname) {
		String sql = """
			    SELECT COUNT(m.member_id)
			    FROM member m
			    WHERE nickname = :nickname
			""";
		SqlParameterSource param = new MapSqlParameterSource("nickname", nickname);
		Integer count = jdbcTemplate.queryForObject(sql, param, Integer.class);
		return count != null && count > 0;
	}

	@Override
	public Optional<Member> findByIdAndIsActiveTrue(Long memberId) {
		String sql = """
			    SELECT member_id,
			           email,
			           password,
			           nickname,
			           profile_image,
			           is_active
			    FROM member
			    WHERE member_id = :memberId
			      AND is_active = TRUE
			""";
		SqlParameterSource param = new MapSqlParameterSource("memberId", memberId);
		return jdbcTemplate.query(sql, param, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToMember(resultSet));
			}
			return Optional.empty();
		});
	}

	@Override
	public Optional<Member> findByEmailAndIsActiveTrue(String email) {
		String sql = """
			    SELECT member_id,
			           email,
			           password,
			           nickname,
			           profile_image,
			           is_active
			    FROM member m
			    WHERE email = :email
			      AND is_active = TRUE
			""";
		SqlParameterSource param = new MapSqlParameterSource("email", email);
		return jdbcTemplate.query(sql, param, resultSet -> {
			if (resultSet.next()) {
				return Optional.of(mapRowToMember(resultSet));
			}
			return Optional.empty();
		});
	}

	private Member mapRowToMember(ResultSet rs) throws SQLException {
		return Member.builder()
			.id(rs.getLong("member_id"))
			.email(rs.getString("email"))
			.password(rs.getString("password"))
			.nickname(rs.getString("nickname"))
			.profileImage(rs.getString("profile_image"))
			.isActive(rs.getBoolean("is_active"))
			.build();
	}
}

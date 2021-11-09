package com.hogwarts.sns.infrastructure.persistence;

import static com.hogwarts.sns.domain.QFollow.*;
import static com.hogwarts.sns.domain.QPost.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils;

import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.QUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TimelineRepository {

	private final JPAQueryFactory queryFactory;

	public List<Post> findByJoinFollow(Long userId, Long lastPostId, Pageable pageable) {
		QueryResults<Post> result = queryFactory
			.selectFrom(post)
			.join(follow)
			.on(eqFollowing(follow.following))
			.where(post.id.lt(lastPostId).and(eqFollower(userId)))
			.limit(pageable.getPageSize())
			.orderBy(post.id.desc())
			.fetchResults();
		return result.getResults();
	}

	public List<Post> findByFirstJoinFollow(Long userId, Pageable pageable) {
		QueryResults<Post> result = queryFactory
			.selectFrom(post)
			.join(follow)
			.on(eqFollowing(follow.following))
			.where(eqFollower(userId))
			.limit(pageable.getPageSize())
			.orderBy(post.id.desc())
			.fetchResults();
		return result.getResults();
	}

	private BooleanExpression eqFollowing(QUser user) {
		if (ObjectUtils.isEmpty(user)) {
			return null;
		}
		return post.user.eq(user);
	}

	private BooleanExpression eqFollower(Long id) {
		if (ObjectUtils.isEmpty(id)) {
			return null;
		}
		return follow.follower.id.eq(id);
	}

}

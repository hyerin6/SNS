package com.hogwarts.sns.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long userId, Pageable Pageable);

	List<Post> findByUserIdAndIdLessThan(@Param("userId") Long userId, @Param("id") Long lastPostId, Pageable Pageable);

	@Query(value = "SELECT p"
		+ " FROM Post p"
		+ " JOIN Follow f ON p.user.id = f.follower.id"
		+ " WHERE p.user.id = :userId"
		+ " AND  f.follower.id = :userId")
	List<Post> findByJoinFollow(@Param("userId") Long userId, Pageable pageable);

	@Query(value = "SELECT p"
		+ " FROM Post p"
		+ " JOIN Follow f ON p.user.id = f.follower.id"
		+ " WHERE p.user.id = :userId"
		+ " AND  f.follower.id = :userId"
		+ " AND p.id < :lastPostId")
	List<Post> findByJoinFollowAndLastIdLessThan(@Param("userId") Long userId, @Param("lastPostId") Long lastPostId,
		Pageable pageable);
}

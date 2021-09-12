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

	@Query(value = "SELECT *"
		+ " FROM post p"
		+ " JOIN follow f ON p.user_id = f.follower_id"
		+ " WHERE p.user_id = :userId"
		+ " AND  f.follower_id = :userId", nativeQuery = true)
	List<Post> findByJoinFollow(Long userId, Pageable pageable);

	@Query(value = "SELECT *"
		+ " FROM post p"
		+ " JOIN follow f ON p.user_id = f.follower_id"
		+ " WHERE p.user_id = :userId"
		+ " AND  f.follower_id = :userId"
		+ " AND p.id < :lastPostId", nativeQuery = true)
	List<Post> findByLastIdAndJoinFollow(Long userId, Long lastPostId, Pageable pageable);
}

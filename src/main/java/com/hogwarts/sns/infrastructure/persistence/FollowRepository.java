package com.hogwarts.sns.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Follow.PK> {

	@Query("SELECT fow FROM Follow fow WHERE fow.follower.id = :followerId")
	List<Follow> findByFollower(Long followerId, Pageable pageable);

	@Query("SELECT fow FROM Follow fow WHERE fow.following.id = :followingId")
	List<Follow> findByFollowing(Long followingId, Pageable pageable);
}

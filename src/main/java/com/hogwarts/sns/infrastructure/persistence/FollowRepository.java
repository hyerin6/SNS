package com.hogwarts.sns.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Follow.PK> {

	Page<Follow> findByFollower(Long followerId, Pageable pageable);

	Page<Follow> findByFollowing(Long followingId, Pageable pageable);

}

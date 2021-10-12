package com.hogwarts.sns.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Follow.PK> {

	List<Follow> findByFollowerId(Long followerId, Pageable pageable);

	List<Follow> findByFollowingId(Long followingId, Pageable pageable);
}

package com.hogwarts.sns.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}

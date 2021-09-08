package com.hogwarts.sns.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByUserId(Long userId, Pageable Pageable);
}

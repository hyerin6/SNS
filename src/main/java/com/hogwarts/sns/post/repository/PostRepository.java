package com.hogwarts.sns.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}

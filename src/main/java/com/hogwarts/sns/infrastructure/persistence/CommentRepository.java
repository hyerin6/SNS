package com.hogwarts.sns.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(Long postId);
}

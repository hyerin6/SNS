package com.hogwarts.sns.infrastructure.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUserId(Long userId, Pageable Pageable);

	List<Post> findByUserIdAndIdLessThan(@Param("userId") Long userId, @Param("id") Long lastPostId, Pageable Pageable);

}

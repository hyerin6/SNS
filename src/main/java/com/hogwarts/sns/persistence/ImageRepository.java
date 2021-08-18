package com.hogwarts.sns.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByPostId(Long postId);

	void deleteAllByPostId(Long postId);
}

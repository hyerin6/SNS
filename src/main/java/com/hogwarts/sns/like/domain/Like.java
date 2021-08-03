package com.hogwarts.sns.like.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.hogwarts.sns.post.domain.Post;
import com.hogwarts.sns.user.domain.User;

@Entity
public class Like {
	@EmbeddedId
	private LikeId id;

	@MapsId("user_id")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@MapsId("post_id")
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

}

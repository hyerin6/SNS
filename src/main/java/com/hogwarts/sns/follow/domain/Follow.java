package com.hogwarts.sns.follow.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.hogwarts.sns.user.domain.User;

@Entity
public class Follow {
	@EmbeddedId
	private FollowId id;

	@MapsId("follower_id")
	@ManyToOne
	@JoinColumn(name = "follower_id")
	private User follower;

	@MapsId("following_id")
	@ManyToOne
	@JoinColumn(name = "following_id")
	private User following;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime created_at;

	@LastModifiedDate
	private LocalDateTime updated_at;

}

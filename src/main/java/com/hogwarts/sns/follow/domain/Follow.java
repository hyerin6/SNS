package com.hogwarts.sns.follow.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hogwarts.sns.user.domain.User;

import lombok.Data;

@Entity
@IdClass(Follow.PK.class)
public class Follow {

	@Id
	@ManyToOne
	@JoinColumn(name = "follower_id")
	private User follower;

	@Id
	@ManyToOne
	@JoinColumn(name = "following_id")
	private User following;

	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Data
	public static class PK implements Serializable {
		@Column(name = "follower_id")
		private long follower;

		@Column(name = "following_id")
		private long following;
	}

}

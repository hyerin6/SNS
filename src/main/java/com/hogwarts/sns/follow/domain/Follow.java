package com.hogwarts.sns.follow.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Data
	public static class PK implements Serializable {
		@Column(name = "follower_id")
		private long follower;

		@Column(name = "following_id")
		private long following;
	}

}

package com.hogwarts.sns.post.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.hogwarts.sns.user.domain.User;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime created_at;

	@LastModifiedDate
	private LocalDateTime updated_at;
}

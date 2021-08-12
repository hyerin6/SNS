package com.hogwarts.sns.like.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.hogwarts.sns.like.domain.enums.LikeTypeConverter;
import com.hogwarts.sns.like.domain.enums.Type;
import com.hogwarts.sns.user.domain.User;

@Entity
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Convert(converter = LikeTypeConverter.class)
	private Type type;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private Long parentId;

	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
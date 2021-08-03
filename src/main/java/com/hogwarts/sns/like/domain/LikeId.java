package com.hogwarts.sns.like.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LikeId implements Serializable {
	@Column(name = "user_id")
	private long userId;

	@Column(name = "post_id")
	private long postId;
}

package com.hogwarts.sns.follow.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowId implements Serializable {
	@Column(name = "follower_id")
	private long followerId;

	@Column(name = "following_id")
	private long followingId;
	
}

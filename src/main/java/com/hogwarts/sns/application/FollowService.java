package com.hogwarts.sns.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Follow;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.FollowRepository;
import com.hogwarts.sns.presentation.response.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {

	private final FollowRepository followRepository;

	@Transactional
	public void follow(User user, User following) {
		Follow follow = Follow.builder()
			.follower(user)
			.following(following)
			.build();

		followRepository.save(follow);
	}

	@Transactional
	public void unFollow(User follower, User following) {
		Follow.PK id = new Follow.PK(follower, following);
		followRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Page<UserResponse> getFollowers(Long userId, Pageable pageable) {
		Page<Follow> followers = followRepository.findByFollowing(userId, pageable);

		List<UserResponse> userResponses = followers.getContent().stream()
			.map(Follow::getFollower).map(UserResponse::new).collect(Collectors.toList());

		return new PageImpl<>(userResponses, pageable, followers.getTotalElements());
	}
	
	@Transactional(readOnly = true)
	public Page<UserResponse> getFollowings(Long userId, Pageable pageable) {
		Page<Follow> followings = followRepository.findByFollower(userId, pageable);

		List<UserResponse> userResponses = followings.getContent().stream()
			.map(Follow::getFollowing).map(UserResponse::new).collect(Collectors.toList());

		return new PageImpl<>(userResponses, pageable, followings.getTotalElements());
	}

}

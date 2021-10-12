package com.hogwarts.sns.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Follow;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.FollowRepository;

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
	public List<User> getFollowers(Long userId, Pageable pageable) {
		return followRepository.findByFollowingId(userId, pageable)
			.stream()
			.map(Follow::getFollower)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<User> getFollowings(Long userId, Pageable pageable) {
		return followRepository.findByFollowerId(userId, pageable)
			.stream()
			.map(Follow::getFollowing)
			.collect(Collectors.toList());
	}

}

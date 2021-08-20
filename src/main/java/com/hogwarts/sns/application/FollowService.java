package com.hogwarts.sns.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hogwarts.sns.domain.Follow;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.persistence.FollowRepository;

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

	public List<User> getFollowers(Long userId) {
		List<Follow> followers = followRepository.findByFollowing(userId);
		return followers.stream().map(Follow::getFollower).collect(Collectors.toList());
	}

	public List<User> getFollowings(Long userId) {
		List<Follow> followings = followRepository.findByFollower(userId);
		return followings.stream().map(Follow::getFollowing).collect(Collectors.toList());
	}

}

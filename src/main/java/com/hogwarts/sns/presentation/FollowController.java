package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.FollowService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.AuthenticationPrincipal;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.response.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowController {

	private final FollowService followService;

	private final UserService userService;

	@GetMapping("/follow/{followingId}")
	public ResponseEntity<Void> follow(@AuthenticationPrincipal String userId, @PathVariable Long followingId) throws
		ResponseException {
		User user = userService.getUser(userId);
		User following = userService.getUser(1L);
		followService.follow(user, following);
		return CREATED;
	}

	@DeleteMapping("/follow/{followingId}")
	public ResponseEntity<Void> unfollow(@AuthenticationPrincipal String userId, @PathVariable Long followingId) throws
		ResponseException {
		User follower = userService.getUser(userId);
		User following = userService.getUser(followingId);
		followService.unFollow(follower, following);
		return OK;
	}

	@GetMapping("/followers")
	public ResponseEntity<List<UserResponse>> getFollowers(@AuthenticationPrincipal String userId, Pageable pageable) {
		User user = userService.getUser(userId);

		List<UserResponse> userResponses = followService.getFollowers(user.getId(), pageable)
			.stream()
			.map(UserResponse::new)
			.collect(Collectors.toList());

		return ResponseEntity.ok(userResponses);
	}

	@GetMapping("/followings")
	public ResponseEntity<List<UserResponse>> getFollowings(@AuthenticationPrincipal String userId, Pageable pageable) {
		User user = userService.getUser(userId);

		List<UserResponse> userResponses = followService.getFollowings(user.getId(), pageable)
			.stream()
			.map(UserResponse::new)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(userResponses);
	}

}

package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import org.springframework.data.domain.Page;
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
import com.hogwarts.sns.infrastructure.security.UserContext;
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
	public ResponseEntity<Void> follow(@PathVariable Long followingId) throws ResponseException {
		User user = UserContext.getCurrentUser();
		User following = userService.getUser(1L);
		followService.follow(user, following);
		return CREATED;
	}

	@DeleteMapping("/follow/{followingId}")
	public ResponseEntity<Void> unfollow(@PathVariable Long followingId) throws ResponseException {
		User follower = UserContext.getCurrentUser();
		User following = userService.getUser(followingId);
		followService.unFollow(follower, following);
		return OK;
	}

	@GetMapping("/followers")
	public ResponseEntity<Page<UserResponse>> getFollowers(Pageable pageable) {
		User user = UserContext.getCurrentUser();
		return ResponseEntity.ok(followService.getFollowers(user.getId(), pageable));
	}

	@GetMapping("/followings")
	public ResponseEntity<Page<UserResponse>> getFollowings(Pageable pageable) {
		User user = UserContext.getCurrentUser();
		return ResponseEntity.ok(followService.getFollowings(user.getId(), pageable));
	}

}

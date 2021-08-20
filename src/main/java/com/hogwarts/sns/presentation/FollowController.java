package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.FollowService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.exception.ResponseException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FollowController {

	private final FollowService followService;
	private final UserService userService;

	@GetMapping("/follows/{followingId}")
	public ResponseEntity<Void> follow(@PathVariable Long followingId) throws ResponseException {
		User user = userService.getUser(1L); // User CRUD 구현 후 수정
		User following = userService.getUser(1L);
		followService.follow(user, following);
		return CREATED;
	}

	@DeleteMapping("/follows/{followingId}")
	public ResponseEntity<Void> unfollow(@PathVariable Long followingId) throws ResponseException {
		User follower = userService.getUser(1L); // User CRUD 구현 후 수정
		User following = userService.getUser(followingId);
		followService.unFollow(follower, following);
		return OK;
	}

	@GetMapping("/followers")
	public ResponseEntity<List<User>> getFollowers() throws ResponseException {
		User user = userService.getUser(1L); // User CRUD 구현 후 수정
		return ResponseEntity.ok(followService.getFollowers(user.getId()));
	}

	@GetMapping("/followings")
	public ResponseEntity<List<User>> getFollowings() throws ResponseException {
		User user = userService.getUser(1L); // User CRUD 구현 후 수정
		return ResponseEntity.ok(followService.getFollowings(user.getId()));
	}

}

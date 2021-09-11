package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.PostService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.Authenticationprincipal;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.ModifyPostRequest;
import com.hogwarts.sns.presentation.request.PostsRequest;
import com.hogwarts.sns.presentation.response.PostResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

	private static final int PAGE = 0;
	private static final int SIZE = 5;
	private static final String SORT_PROPERTY = "id";

	private final PostService postService;
	private final UserService userService;

	@PostMapping("/post")
	public ResponseEntity<Void> create(@Authenticationprincipal String userId,
		@ModelAttribute CreatePostRequest request) {
		User user = userService.getUser(userId);
		postService.addPost(user, request);
		return CREATED;
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<PostResponse> getContent(@PathVariable Long id) throws ResponseException {
		return ResponseEntity.ok(postService.getPost(id));
	}

	@PutMapping("/post/{id}")
	public ResponseEntity<Void> modify(@PathVariable Long id, @RequestBody ModifyPostRequest request) throws
		ResponseException {
		postService.modifyPost(id, request);
		return OK;
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		postService.deletePost(id);
		return OK;
	}

	@PostMapping("/posts")
	public ResponseEntity<List<PostResponse>> getMyPosts(@Authenticationprincipal String userId,
		@RequestBody PostsRequest request) {
		User user = userService.getUser(userId);
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY));
		return ResponseEntity.ok(postService.getPosts(user.getId(), request.getLastPostId(), pageRequest));
	}

	@PostMapping("/posts/{userId}")
	public ResponseEntity<List<PostResponse>> getPosts(@PathVariable("userId") Long userId,
		@RequestBody PostsRequest request) {
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY));
		return ResponseEntity.ok(postService.getPosts(userId, request.getLastPostId(), pageRequest));
	}

	@PostMapping("/feed")
	public ResponseEntity<List<PostResponse>> getFeed(@Authenticationprincipal String userId,
		@RequestBody PostsRequest request) {
		User user = userService.getUser(userId);
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY));
		return ResponseEntity.ok(postService.getFeed(user.getId(), request.getLastPostId(), pageRequest));
	}

}

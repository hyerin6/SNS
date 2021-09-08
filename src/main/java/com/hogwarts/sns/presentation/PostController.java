package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.UserContext;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.ModifyPostRequest;
import com.hogwarts.sns.presentation.response.PostResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

	private final PostService postService;

	@PostMapping("/post")
	public ResponseEntity<Void> create(@ModelAttribute CreatePostRequest request) {
		User user = UserContext.getCurrentUser();
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

	@GetMapping("/posts")
	public ResponseEntity<Page<PostResponse>> getMyPosts(Pageable pageable) {
		User user = UserContext.getCurrentUser();
		return ResponseEntity.ok(postService.getPosts(user.getId(), pageable));
	}

	@GetMapping("/posts/{userId}")
	public ResponseEntity<Page<PostResponse>> getPosts(@PathVariable("userId") Long userId, Pageable pageable) {
		return ResponseEntity.ok(postService.getPosts(userId, pageable));
	}

	@GetMapping("/feed")
	public ResponseEntity<Page<PostResponse>> getFeed(Pageable pageable) {
		User user = UserContext.getCurrentUser();
		return ResponseEntity.ok(postService.getFeed(user.getId(), pageable));
	}

}

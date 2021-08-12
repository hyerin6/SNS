package com.hogwarts.sns.post.controller;

import static com.hogwarts.sns.common.response.ResponseEntityConstants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.post.dto.CreatePostRequest;
import com.hogwarts.sns.post.dto.FeedResponse;
import com.hogwarts.sns.post.dto.ModifyPostRequest;
import com.hogwarts.sns.post.dto.PostResponse;
import com.hogwarts.sns.post.service.PostService;
import com.hogwarts.sns.user.domain.User;
import com.hogwarts.sns.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostController {

	private final PostService postService;
	private final UserRepository userRepository;

	@PostMapping("/posts")
	public ResponseEntity<Void> create(@ModelAttribute CreatePostRequest request) {
		User user = userRepository.findAll().get(0); // User CRUD 구현 후 수정
		postService.addPost(user, request);
		return CREATED;
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<PostResponse> getContent(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPost(id));
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<Void> modify(@PathVariable Long id, @RequestBody ModifyPostRequest request) {
		postService.modifyPost(id, request);
		return OK;
	}

	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		postService.deletePost(id);
		return OK;
	}

	@GetMapping("/posts")
	public ResponseEntity<FeedResponse> getPosts() {
		return null;
	}

	@GetMapping("/feed")
	public ResponseEntity<FeedResponse> getFeed() {
		return null;
	}

}

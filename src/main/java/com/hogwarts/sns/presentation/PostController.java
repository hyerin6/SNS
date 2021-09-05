package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

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
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.ModifyPostRequest;
import com.hogwarts.sns.presentation.response.FeedResponse;
import com.hogwarts.sns.presentation.response.PostResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

	private final PostService postService;
	private final UserService userService;

	@PostMapping("posts")
	public ResponseEntity<Void> create(@ModelAttribute CreatePostRequest request) throws ResponseException {
		User user = userService.getUser(1L); // User CRUD 구현 후 수정
		postService.addPost(user, request);
		return CREATED;
	}

	@GetMapping("posts/{id}")
	public ResponseEntity<PostResponse> getContent(@PathVariable Long id) throws ResponseException {
		return ResponseEntity.ok(postService.getPost(id));
	}

	@PutMapping("posts/{id}")
	public ResponseEntity<Void> modify(@PathVariable Long id, @RequestBody ModifyPostRequest request) throws
		ResponseException {
		postService.modifyPost(id, request);
		return OK;
	}

	@DeleteMapping("posts/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		postService.deletePost(id);
		return OK;
	}

	@GetMapping("posts")
	public ResponseEntity<FeedResponse> getPosts() {
		return null;
	}

	@GetMapping("feed")
	public ResponseEntity<FeedResponse> getFeed() {
		return null;
	}

}

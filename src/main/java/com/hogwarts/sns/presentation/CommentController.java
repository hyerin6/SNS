package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.CommentService;
import com.hogwarts.sns.application.PostService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.Comment;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.exception.ResponseException;
import com.hogwarts.sns.presentation.request.CreateCommentRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;
	private final PostService postService;
	private final UserService userService;

	@PostMapping("/comments")
	public ResponseEntity<Void> create(@RequestBody CreateCommentRequest request) throws ResponseException {
		User user = userService.getUser(1L); // User CRUD 구현 후 수정
		Post post = postService.getPost(request.getPostId()).getPost();
		commentService.create(user, post, request);
		return CREATED;
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ResponseException {
		commentService.delete(id);
		return OK;
	}

	@GetMapping("/comments/{postId}")
	public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
		return ResponseEntity.ok(commentService.getComments(postId));
	}

}

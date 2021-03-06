package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.CommentService;
import com.hogwarts.sns.application.HeartService;
import com.hogwarts.sns.application.PostService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.Comment;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.Type;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.AuthenticationPrincipal;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.request.CreateCommentRequest;
import com.hogwarts.sns.presentation.request.HeartRequest;
import com.hogwarts.sns.presentation.response.CommentResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

	private final CommentService commentService;
	private final PostService postService;
	private final HeartService heartService;
	private final UserService userService;

	@PostMapping("/comment")
	public ResponseEntity<Void> create(@AuthenticationPrincipal String userId,
		@RequestBody CreateCommentRequest request) throws ResponseException {
		User user = userService.getUser(userId);
		Post post = postService.getPost(request.getPostId());
		commentService.create(user, post, request);
		return CREATED;
	}

	@DeleteMapping("/comment/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ResponseException {
		commentService.delete(id);
		return OK;
	}

	@GetMapping("/comments/{postId}")
	public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
		List<Comment> comments = commentService.getComments(postId);

		List<CommentResponse> commentResponses = new ArrayList<>();
		for (Comment comment : comments) {
			int heartCnt = heartService.getHeartCnt(new HeartRequest(Type.COMMENT, comment.getId()));
			commentResponses.add(new CommentResponse(comment, heartCnt));
		}

		return ResponseEntity.ok(commentResponses);
	}

}

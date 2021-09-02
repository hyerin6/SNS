package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.LikeService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.request.LikeRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;
	private final UserService userService;

	@PostMapping("/like")
	public ResponseEntity<Void> like(@RequestBody LikeRequest request) throws ResponseException {
		User user = userService.getUser(1L); // User CRUD 구현 후 수정
		likeService.Like(user, request);
		return CREATED;
	}

	@DeleteMapping("/unLike/{id}")
	public ResponseEntity<Void> unLike(@PathVariable Long id) {
		likeService.unLike(id);
		return OK;
	}

}

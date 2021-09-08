package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.LikeService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.UserContext;
import com.hogwarts.sns.presentation.request.LikeRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("like")
	public ResponseEntity<Void> like(@RequestBody LikeRequest request) {
		User user = UserContext.getCurrentUser();
		likeService.Like(user, request);
		return CREATED;
	}

	@DeleteMapping("unLike/{id}")
	public ResponseEntity<Void> unLike(@PathVariable Long id) {
		likeService.unLike(id);
		return OK;
	}

}

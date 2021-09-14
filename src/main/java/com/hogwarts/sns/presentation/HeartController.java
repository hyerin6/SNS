package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.HeartService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.AuthenticationPrincipal;
import com.hogwarts.sns.presentation.request.HeartRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HeartController {

	private final HeartService heartService;
	private final UserService userService;

	@PostMapping("/like")
	public ResponseEntity<Void> like(@AuthenticationPrincipal String userId, @RequestBody HeartRequest request) {
		User user = userService.getUser(userId);
		heartService.create(user, request);
		return CREATED;
	}

	@DeleteMapping("/unLike/{id}")
	public ResponseEntity<Void> unLike(@PathVariable Long id) {
		heartService.delete(id);
		return OK;
	}

}

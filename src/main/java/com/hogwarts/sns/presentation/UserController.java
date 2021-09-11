package com.hogwarts.sns.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.Authenticationprincipal;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.response.UserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/myInfo")
	public ResponseEntity<UserResponse> getMyInfo(@Authenticationprincipal String userId) {
		User user = userService.getUser(userId);
		return ResponseEntity.ok(new UserResponse(user));
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) throws ResponseException {
		User user = userService.getUser(id);
		return ResponseEntity.ok(new UserResponse(user));
	}

}

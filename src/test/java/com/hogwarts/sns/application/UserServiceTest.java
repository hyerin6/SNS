package com.hogwarts.sns.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.UserRepository;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.exception.e4xx.NotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserService userService;

	User user;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.id(1L)
			.userId("123")
			.name("박혜린")
			.profile("profile image url")
			.email("hyerin_0611@naver.com")
			.build();
	}

	@DisplayName("id로 조회 성공")
	@Test
	void getUserById() throws ResponseException {
		given(userRepository.findById(eq(1L))).willReturn(Optional.of(user));

		User user = userService.getUser(1L);

		assertEquals(user.getName(), "박혜린");
		assertEquals(user.getUserId(), "123");
		assertEquals(user.getProfile(), "profile image url");
		assertEquals(user.getEmail(), "hyerin_0611@naver.com");
	}

	@DisplayName("userId로 조회 성공")
	@Test
	void getUserByUserId() {
		given(userRepository.findByUserId(eq("123"))).willReturn(Optional.of(user));

		User user = userService.getUser("123");

		assertEquals(user.getId(), 1L);
		assertEquals(user.getName(), "박혜린");
		assertEquals(user.getProfile(), "profile image url");
		assertEquals(user.getEmail(), "hyerin_0611@naver.com");
	}

	@DisplayName("id로 조회: User 없으면 Exception 발생")
	@Test
	void failToGetUserByIdIfNotFound() throws ResponseException {
		given(userRepository.findById(eq(2L)))
			.willReturn(Optional.empty());

		ResponseException nfe = NotFoundException.USER.getResponseException();

		ResponseException exception = assertThrows(
			nfe.getClass(),
			() -> userService.getUser(2L)
		);

		assertEquals(exception.getCode(), nfe.getCode());
		assertEquals(exception.getCode(), nfe.getCode());
		assertEquals(exception.getCode(), nfe.getCode());
	}

	@DisplayName("userId로 조회: User 없으면 null 리턴")
	@Test
	void failToGetUserIdByIdIfNotFound() {
		given(userRepository.findByUserId(eq("456")))
			.willReturn(Optional.empty());

		assertNull(userService.getUser("456"));
	}

}
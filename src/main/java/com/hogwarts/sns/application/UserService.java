package com.hogwarts.sns.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.UserRepository;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.exception.e4xx.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public void create(User user) {
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User getUser(Long id) throws ResponseException {
		return userRepository.findById(id).orElseThrow(NotFoundException.USER);
	}

	@Transactional(readOnly = true)
	public User getUser(String userId) {
		return userRepository.findByUserId(userId).orElse(null);
	}

}

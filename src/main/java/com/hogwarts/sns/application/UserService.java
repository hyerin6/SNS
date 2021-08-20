package com.hogwarts.sns.application;

import org.springframework.stereotype.Service;

import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.exception.ResponseException;
import com.hogwarts.sns.exception.e4xx.NotFoundException;
import com.hogwarts.sns.persistence.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public User getUser(Long id) throws ResponseException {
		return userRepository.findById(id).orElseThrow(NotFoundException.USER);
	}

}

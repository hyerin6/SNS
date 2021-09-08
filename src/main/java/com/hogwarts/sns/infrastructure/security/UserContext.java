package com.hogwarts.sns.infrastructure.security;

import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.presentation.exception.e4xx.AuthenticationException;

public class UserContext {

	public static final ThreadLocal<User> USER_CONTEXT = new ThreadLocal<>();

	public static User getCurrentUser() {
		if (UserContext.USER_CONTEXT.get() != null) {
			return UserContext.USER_CONTEXT.get();
		}

		throw new AuthenticationException();
	}

}

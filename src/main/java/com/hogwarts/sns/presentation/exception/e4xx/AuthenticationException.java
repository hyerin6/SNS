package com.hogwarts.sns.presentation.exception.e4xx;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {
	}

	public AuthenticationException(Throwable t) {
		super(t);
	}

	public AuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
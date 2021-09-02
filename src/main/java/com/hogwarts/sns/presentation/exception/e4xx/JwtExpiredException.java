package com.hogwarts.sns.presentation.exception.e4xx;

public class JwtExpiredException extends RuntimeException {

	public JwtExpiredException() {
	}

	public JwtExpiredException(Throwable t) {
		super(t);
	}

	public JwtExpiredException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
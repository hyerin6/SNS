package com.hogwarts.sns.presentation.exception.e4xx;

public class JwtException extends RuntimeException {

	public JwtException() {
	}

	public JwtException(Throwable t) {
		super(t);
	}

	public JwtException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
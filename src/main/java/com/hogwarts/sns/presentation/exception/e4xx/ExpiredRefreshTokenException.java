package com.hogwarts.sns.presentation.exception.e4xx;

import lombok.Getter;

@Getter
public class ExpiredRefreshTokenException extends RuntimeException {

	private String uid;

	public ExpiredRefreshTokenException() {
	}

	public ExpiredRefreshTokenException(Throwable t, String uid) {
		super(t);
		this.uid = uid;
	}

	public ExpiredRefreshTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
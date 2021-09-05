package com.hogwarts.sns.presentation.exception.e4xx;

import lombok.Getter;

@Getter
public class ExpiredAccessTokenException extends RuntimeException {

	private String uid;

	public ExpiredAccessTokenException() {
	}

	public ExpiredAccessTokenException(Throwable t, String uid) {
		super(t);
		this.uid = uid;
	}

	public ExpiredAccessTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
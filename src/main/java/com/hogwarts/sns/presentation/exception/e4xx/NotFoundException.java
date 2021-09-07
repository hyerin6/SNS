package com.hogwarts.sns.presentation.exception.e4xx;

import org.springframework.http.HttpStatus;

import com.hogwarts.sns.presentation.exception.ResponseDefinition;
import com.hogwarts.sns.presentation.exception.ResponseException;

public enum NotFoundException implements ResponseDefinition {
	POST(HttpStatus.BAD_REQUEST, NotFoundException.NOT_FOUND_CODE, "해당 게시글이 존재하지 않습니다."),
	COMMENT(HttpStatus.BAD_REQUEST, NotFoundException.NOT_FOUND_CODE, "해당 댓글이 존재하지 않습니다."),
	USER(HttpStatus.BAD_REQUEST, NotFoundException.NOT_FOUND_CODE, "해당 사용자가 존재하지 않습니다.");

	private static final int NOT_FOUND_CODE = 404;

	private final ResponseException responseException;

	NotFoundException(HttpStatus status, Integer code, String message) {
		this.responseException = new ResponseException(status, code, message);
	}

	@Override
	public ResponseException getResponseException() {
		return responseException;
	}
}

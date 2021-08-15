package com.hogwarts.sns.exception.e4xx;

import com.hogwarts.sns.exception.ResponseDefinition;
import com.hogwarts.sns.exception.ResponseException;
import org.springframework.http.HttpStatus;

public enum NotFoundException implements ResponseDefinition {
    POST(HttpStatus.BAD_REQUEST, NotFoundException.NOT_FOUND_CODE, "포스트가 없어용")
    ;

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

package com.hogwarts.sns.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResponseException extends Exception {

    @EqualsAndHashCode.Include
    private int status;

    @EqualsAndHashCode.Include
    private Integer code;

    @EqualsAndHashCode.Include
    private String message;



    private ResponseException(ResponseException responseException) {
        this.code = responseException.code;
        this.message = responseException.message;
    }

    public ResponseException(HttpStatus status, Integer code, String message) {
        this.status = status.value();
        this.code = code;
        this. message = message;
    }

    @Override
    protected ResponseException clone() {
        return new ResponseException(this);
    }
}

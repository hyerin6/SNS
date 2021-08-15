package com.hogwarts.sns.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseException.class)
    public ResponseException processException(HttpServletRequest request, ResponseException responseException) {
        System.out.println(responseException.toString());
        return responseException;
    }
}

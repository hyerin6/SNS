package com.hogwarts.sns.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hogwarts.sns.exception.e5xx.FileUploadException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(ResponseException.class)
	public ResponseException processException(HttpServletRequest request, ResponseException responseException) {
		log.error(responseException.toString());
		return responseException;
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseException fileUploadException(Exception exception) {
		log.error("error", exception);
		return new ResponseException(
			HttpStatus.INTERNAL_SERVER_ERROR,
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			exception.getMessage()
		);
	}

}

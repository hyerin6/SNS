package com.hogwarts.sns.presentation.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hogwarts.sns.application.JwtService;
import com.hogwarts.sns.application.RedisService;
import com.hogwarts.sns.presentation.exception.e4xx.JwtException;
import com.hogwarts.sns.presentation.exception.e5xx.FileUploadException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionControllerAdvice {

	private final JwtService jwtService;

	private final RedisService redisService;

	@ExceptionHandler(ResponseException.class)
	public ResponseEntity<ResponseException> processException(HttpServletRequest request, ResponseException response) {
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public void expiredJwtException() {
	}

	@ExceptionHandler(JwtException.class)
	public void jwtException() {
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<ResponseException> fileUploadException(Exception exception) {
		ResponseException responseException = new ResponseException(
			HttpStatus.INTERNAL_SERVER_ERROR,
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			exception.getMessage()
		);
		return new ResponseEntity<>(responseException, HttpStatus.valueOf(responseException.getStatus()));
	}

}

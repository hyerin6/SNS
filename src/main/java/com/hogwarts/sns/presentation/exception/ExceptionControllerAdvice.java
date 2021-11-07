package com.hogwarts.sns.presentation.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.hogwarts.sns.infrastructure.security.JwtService;
import com.hogwarts.sns.presentation.exception.e4xx.AuthenticationException;
import com.hogwarts.sns.presentation.exception.e4xx.ExpiredAccessTokenException;
import com.hogwarts.sns.presentation.exception.e4xx.ExpiredRefreshTokenException;
import com.hogwarts.sns.presentation.exception.e5xx.FileUploadException;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionControllerAdvice {

	private final JwtService jwtService;

	private final RedisTemplate<String, String> redisTemplate;

	@ExceptionHandler(ResponseException.class)
	public ResponseEntity<ResponseException> processException(HttpServletRequest request, ResponseException response) {
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
	}

	@ExceptionHandler({AuthenticationException.class, JwtException.class})
	public ModelAndView AuthenticationException(Exception e) {
		return new ModelAndView("index");
	}

	@ExceptionHandler(ExpiredAccessTokenException.class)
	public ModelAndView expiredAccessTokenException(ExpiredAccessTokenException e) {
		String userId = e.getSub();

		if (!redisTemplate.hasKey(userId)) {
			return new ModelAndView("index");
		}

		String refreshToken = redisTemplate.opsForValue().get(userId);
		jwtService.verifyRefreshToken(refreshToken);
		String accessToken = jwtService.createAccessToken(userId);
		return new ModelAndView("login", "accessToken", accessToken);
	}

	@ExceptionHandler(ExpiredRefreshTokenException.class)
	public ModelAndView expiredRefreshTokenException(ExpiredRefreshTokenException e) {
		return new ModelAndView("index");
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

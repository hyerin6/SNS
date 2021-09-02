package com.hogwarts.sns.infrastructure.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hogwarts.sns.application.JwtService;
import com.hogwarts.sns.application.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

	private static final String HEADER_AUTH = "Authorization";

	private final JwtService jwtService;

	private final RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response, Object handler) throws Exception {
		String accessToken = request.getHeader(HEADER_AUTH);
		String refreshToken = redisService.getValue(accessToken);
		String accessTokenUid = jwtService.decode(accessToken);
		String refreshTokenUid = jwtService.decode(refreshToken);
		return super.preHandle(request, response, handler);
	}
}

package com.hogwarts.sns.infrastructure.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hogwarts.sns.infrastructure.security.JwtService;
import com.hogwarts.sns.presentation.exception.e4xx.AuthenticationException;
import com.hogwarts.sns.utils.HeaderUtils;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class JwtInterceptor extends HandlerInterceptorAdapter {

	private final JwtService jwtService;

	@SneakyThrows
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String accessToken = HeaderUtils.getAccessToken(request);

		jwtService.verifyAccessToken(accessToken);
		String userId = jwtService.decode(accessToken);

		if (!jwtService.hasRefreshToken(userId)) {
			throw new AuthenticationException();
		}

		return true;
	}
}

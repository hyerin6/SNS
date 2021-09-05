package com.hogwarts.sns.infrastructure.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hogwarts.sns.infrastructure.security.AuthService;
import com.hogwarts.sns.infrastructure.security.JwtService;
import com.hogwarts.sns.presentation.exception.e4xx.AuthenticationException;
import com.hogwarts.sns.utils.HeaderUtil;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

	private final AuthService authService;

	private final JwtService jwtService;

	@SneakyThrows
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String accessToken = HeaderUtil.getAccessToken(request);

		jwtService.verifyAccessToken(accessToken);
		String uid = jwtService.decode(accessToken);

		if (!authService.hasRefreshToken(uid)) {
			throw new AuthenticationException();
		}

		return super.preHandle(request, response, handler);
	}
}

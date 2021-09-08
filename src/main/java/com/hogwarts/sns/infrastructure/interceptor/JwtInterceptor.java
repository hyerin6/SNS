package com.hogwarts.sns.infrastructure.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.JwtService;
import com.hogwarts.sns.infrastructure.security.UserContext;
import com.hogwarts.sns.presentation.exception.e4xx.AuthenticationException;
import com.hogwarts.sns.utils.HeaderUtil;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

	private final JwtService jwtService;
	private final UserService userService;
	private final RedisTemplate<String, String> redisTemplate;

	@SneakyThrows
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String accessToken = HeaderUtil.getAccessToken(request);

		jwtService.verifyAccessToken(accessToken);
		String userId = jwtService.decode(accessToken);
		
		if (!jwtService.hasRefreshToken(userId)) {
			throw new AuthenticationException();
		}

		User user = userService.getUser(userId);
		UserContext.USER_CONTEXT.set(user);

		return super.preHandle(request, response, handler);
	}
}

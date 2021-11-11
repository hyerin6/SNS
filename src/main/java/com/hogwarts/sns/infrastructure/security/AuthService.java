package com.hogwarts.sns.infrastructure.security;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.hogwarts.sns.application.FollowService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.presentation.response.KakaoProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	private final FollowService followService;

	private final JwtService jwtService;

	private final StringRedisTemplate redisTemplate;

	public String signUp(KakaoProfile kakaoProfile) {
		User user = User.builder()
			.userId(kakaoProfile.getId())
			.email(kakaoProfile.getKakaoAccount().getEmail())
			.name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.profile(kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
			.build();

		userService.create(user);
		followService.follow(user, user);
		return signIn(user.getUserId());
	}

	public String signIn(String userId) {
		invalidate(userId);
		String accessToken = jwtService.createAccessToken(userId);
		String refreshToken = jwtService.createRefreshToken(userId);
		redisTemplate.opsForValue().set(userId, refreshToken);
		redisTemplate.expire(userId, 30, TimeUnit.DAYS);
		return accessToken;
	}

	public void invalidate(String userId) {
		if (redisTemplate.hasKey(userId)) {
			redisTemplate.delete(userId);
		}
	}

}

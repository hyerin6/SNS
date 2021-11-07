package com.hogwarts.sns.infrastructure.security;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

	private final JwtService jwtService;

	private final RedisTemplate<String, String> redisTemplate;

	public String signUp(KakaoProfile kakaoProfile) {
		User user = User.builder()
			.userId(kakaoProfile.getId())
			.email(kakaoProfile.getKakaoAccount().getEmail())
			.name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.profile(kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
			.build();

		userService.create(user);

		return signIn(user.getUserId());
	}

	public String signIn(String userId) {
		String accessToken = jwtService.createAccessToken(userId);
		String refreshToken = jwtService.createRefreshToken(userId);
		redisTemplate.opsForValue().set(userId, refreshToken);
		redisTemplate.expire(userId, 168, TimeUnit.HOURS);
		return accessToken;
	}

}

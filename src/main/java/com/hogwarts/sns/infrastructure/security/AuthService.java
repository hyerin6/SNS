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
			.uid(kakaoProfile.getId())
			.email(kakaoProfile.getKakaoAccount().getEmail())
			.name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.profile(kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
			.build();

		userService.createUser(user);

		return signIn(user.getUid());
	}

	public String signIn(String uid) {
		invalidate(uid);
		String accessToken = jwtService.createAccessToken(uid);
		String refreshToken = jwtService.createRefreshToken(uid);
		redisTemplate.opsForValue().set(uid, refreshToken);
		redisTemplate.expire(uid, 30, TimeUnit.DAYS);
		return accessToken;
	}

	public void invalidate(String uid) {
		if (redisTemplate.hasKey(uid)) {
			redisTemplate.delete(uid);
		}
	}

}

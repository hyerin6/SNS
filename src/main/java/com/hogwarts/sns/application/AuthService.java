package com.hogwarts.sns.application;

import org.springframework.stereotype.Service;

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

	private final RedisService redisService;

	public void signUp(KakaoProfile kakaoProfile) {
		User user = User.builder()
			.uid(kakaoProfile.getId())
			.email(kakaoProfile.getKakaoAccount().getEmail())
			.name(kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.profile(kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
			.build();

		userService.createUser(user);
		signIn(kakaoProfile.getId());
	}

	public void signIn(String uid) {
		String accessToken = jwtService.getAccessToken(uid);
		redisService.setAccessToken(accessToken, jwtService.getRefreshToken(uid));
	}

}

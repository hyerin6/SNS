package com.hogwarts.sns.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.presentation.response.KakaoProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthorizeController {

	private final AuthApiService authApiService;

	private final AuthService authService;

	private final UserService userService;

	@Value("${signin-uri}")
	private String signinUrl;

	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("/index", "signinUrl", signinUrl);
	}

	@GetMapping("/login/oauth2/code/kakao")
	public ModelAndView callback(@RequestParam("code") String code) {
		String kakaoAccessToken = authApiService.getToken(code);
		KakaoProfile kakaoProfile = authApiService.getProfile(kakaoAccessToken);
		User user = userService.getUser(kakaoProfile.getId());

		String accessToken = "";
		if (user == null) {
			accessToken = authService.signUp(kakaoProfile);
		} else {
			accessToken = authService.signIn(user.getUserId());
		}

		ModelAndView modelAndView = new ModelAndView("/user");
		modelAndView.addObject("userId", kakaoProfile.getId())
			.addObject("email", kakaoProfile.getKakaoAccount().getEmail())
			.addObject("name", kakaoProfile.getKakaoAccount().getProfile().getNickname())
			.addObject("profile", kakaoProfile.getKakaoAccount().getProfile().getProfileImageUrl())
			.addObject("accessToken", accessToken);
		return modelAndView;
	}

}

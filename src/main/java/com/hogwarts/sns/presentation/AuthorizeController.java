package com.hogwarts.sns.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hogwarts.sns.application.AuthApiService;
import com.hogwarts.sns.application.AuthService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.presentation.response.KakaoAccount;
import com.hogwarts.sns.presentation.response.KakaoProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class AuthorizeController {

	private final AuthApiService authApiService;
	private final AuthService authService;
	private final UserService userService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("signinUrl", authApiService.getAuthorizationUrl());
		return "/index";
	}

	@GetMapping("/login/oauth2/code/kakao")
	public String callback(@RequestParam("code") String code, Model model) {
		String accessToken = authApiService.getToken(code);
		KakaoProfile kakaoProfile = authApiService.getProfile(accessToken);
		User user = userService.getUser(kakaoProfile.getId());

		if (user == null) {
			authService.signUp(kakaoProfile);
		} else {
			authService.signIn(user.getUid());
		}

		KakaoAccount.Profile profile = kakaoProfile.getKakaoAccount().getProfile();
		model.addAttribute("id", kakaoProfile.getId());
		model.addAttribute("email", kakaoProfile.getKakaoAccount().getEmail());
		model.addAttribute("nickname", profile.getNickname());
		model.addAttribute("profile", profile.getProfileImageUrl());
		return "/user";
	}

	@GetMapping("/test")
	public void test() {
		log.info("/test");
	}

}

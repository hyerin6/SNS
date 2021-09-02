package com.hogwarts.sns.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hogwarts.sns.presentation.response.KakaoProfile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthApiService {

	private final static String TOKEN_TYPE = "Bearer ";

	@Value("${spring.security.oauth2.kakao.content-type}")
	private String contentType;

	@Value("${spring.security.oauth2.kakao.authorization-grant-type}")
	private String grantType;

	@Value("${spring.security.oauth2.kakao.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.kakao.redirect-uri}")
	private String redirectUri;

	@Value("${spring.security.oauth2.kakao.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.kakao.token-uri}")
	private String tokenUri;

	@Value("${spring.security.oauth2.kakao.user-info-uri}")
	private String userInfoUri;

	public String getAuthorizationUrl() {
		return "https://kauth.kakao.com/oauth/authorize?"
			+ "client_id=" + clientId
			+ "&redirect_uri=" + redirectUri
			+ "&response_type=code";
	}

	public String getToken(String code) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", contentType);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", grantType);
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);
		params.add("client_secret", clientSecret);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<TokenResponse> response = rt.exchange(
			tokenUri,
			HttpMethod.POST,
			request,
			TokenResponse.class
		);

		return response.getBody().getAccessToken();
	}

	public KakaoProfile getProfile(String accessToken) {
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", contentType);
		headers.add("Authorization", TOKEN_TYPE + accessToken);

		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<KakaoProfile> response = rt.exchange(
			userInfoUri,
			HttpMethod.GET,
			request,
			KakaoProfile.class
		);

		return response.getBody();
	}

	@Getter
	@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
	public static class TokenResponse {
		private String accessToken;
		private String tokenType;
		private String refreshToken;
		private String scope;
	}

}

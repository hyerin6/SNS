package com.hogwarts.sns.presentation.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoProfile {
	private Long id;
	private KakaoAccount kakaoAccount;

	public String getId() {
		return String.valueOf(this.id);
	}

	public KakaoAccount getKakaoAccount() {
		return this.kakaoAccount;
	}
}

package com.hogwarts.sns.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtil {

	private static final String HEADER_AUTH = "Authorization";

	private static final String BEARER_PREFIX = "Bearer ";

	public static String getAccessToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_AUTH);

		if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
			return token.substring(7);
		}

		return null;
	}

}

package com.hogwarts.sns.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class HeaderUtils {
	private static final String HEADER_AUTH = "Authorization";

	private static final String BEARER_PREFIX = "Bearer ";

	public static String getAccessToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_AUTH);

		if (StringUtils.hasText(token)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return null;
	}

	public static String getAccessToken(WebRequest request) {
		String token = request.getHeader(HEADER_AUTH);

		if (StringUtils.hasText(token)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return null;
	}

}

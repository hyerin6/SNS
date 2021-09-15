package com.hogwarts.sns.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtil {
	private static final String HEADER_AUTH = "Authorization";

	private static final String BEARER_PREFIX = "Bearer ";

	public static String getAccessToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_AUTH);
		return token.substring(BEARER_PREFIX.length());
	}

	public static String getAccessToken(WebRequest request) {
		String token = request.getHeader(HEADER_AUTH);
		return token.substring(BEARER_PREFIX.length());
	}

}

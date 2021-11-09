package com.hogwarts.sns.utils;

import static com.hogwarts.sns.utils.constants.AuthConstants.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

public class HeaderUtils {

	public static String getAccessToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(token)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return null;
	}

	public static String getAccessToken(WebRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(token)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return null;
	}

}

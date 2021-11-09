package com.hogwarts.sns.utils.constants;

public class AuthConstants {
	public static final String BEARER_PREFIX = "Bearer ";
	public final static String VIEW_INDEX = "index";
	public final static String SIGNIN_URL = "signinUrl";
	public final static String USER = "user";
	public final static String USERID = "userId";
	public final static String EMAIL = "email";
	public final static String NAME = "name";
	public final static String PROFILE = "profile";
	public final static String ACCESS_TOKEN = "accessToken";
	public static final String TOKEN_REGEX = "\\.";
	public static final Integer ACCESS_EXPIRE = 1 * 1000 * 60 * 60 * 24; // 1일
	public static final Integer REFRESH_EXPIRE = 15 * 1000 * 60 * 60 * 24; // 15일
}

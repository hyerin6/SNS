package com.hogwarts.sns.infrastructure.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwarts.sns.presentation.exception.e4xx.AuthenticationException;
import com.hogwarts.sns.presentation.exception.e4xx.ExpiredAccessTokenException;
import com.hogwarts.sns.presentation.exception.e4xx.ExpiredRefreshTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	private static final String TOKEN_REGEX = "\\.";

	private static final Integer ACCESS_EXPIRE = 1 * 1000 * 60 * 60 * 24; // 1일

	private static final Integer REFRESH_EXPIRE = 30 * 1000 * 60 * 60 * 24; // 30일

	public String createAccessToken(String subject) {
		Date now = new Date();
		Date expireTime = new Date(now.getTime() + ACCESS_EXPIRE);

		return Jwts.builder()
			.setExpiration(expireTime)
			.setSubject(subject)
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}

	public String createRefreshToken(String subject) {
		Date now = new Date();
		Date expireTime = new Date(now.getTime() + REFRESH_EXPIRE);

		return Jwts.builder()
			.setExpiration(expireTime)
			.setSubject(subject)
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}

	public void verifyAccessToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			String uid = decode(token);
			throw new ExpiredAccessTokenException(e, uid);
		} catch (JwtException | IllegalArgumentException e) {
			new AuthenticationException(e);
		}
	}

	public void verifyRefreshToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			String uid = decode(token);
			throw new ExpiredRefreshTokenException(e, uid);
		} catch (JwtException | IllegalArgumentException e) {
			new AuthenticationException(e);
		}
	}

	public String decode(String token) {
		String sub = "";

		try {
			String[] chunks = token.split(TOKEN_REGEX);
			String payload = new String(Base64.getDecoder().decode(chunks[1]));
			ObjectMapper mapper = new ObjectMapper();
			sub = mapper.readValue(payload, Payload.class).getSub();
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}

		return sub;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Payload {
		private String exp;
		private String sub;
	}

}

package com.hogwarts.sns.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hogwarts.sns.presentation.exception.e4xx.JwtException;
import com.hogwarts.sns.presentation.exception.e4xx.JwtExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {

	@Value("${jwt.secret}")
	private String SALT;

	private static final Integer accessTokenExpire = 1000 * 60 * 60 * 24;

	private static final Integer refreshTokenExpire = 1000 * 60 * 60 * 24 * 14;

	public String getAccessToken(String subject) {
		Date now = new Date();
		Date expireTime = new Date(now.getTime() + accessTokenExpire);

		return Jwts.builder()
			.setExpiration(expireTime)
			.setSubject(subject)
			.signWith(SignatureAlgorithm.HS256, SALT)
			.compact();
	}

	public String getRefreshToken(String subject) {
		Date now = new Date();
		Date expireTime = new Date(now.getTime() + refreshTokenExpire);

		return Jwts.builder()
			.setExpiration(expireTime)
			.setSubject(subject)
			.signWith(SignatureAlgorithm.HS256, SALT)
			.compact();
	}

	public void verifyToken(String token) {
		try {
			Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException(e);
		} catch (IllegalArgumentException e) {
			throw new JwtException(e);
		} catch (Exception e) {
			throw new JwtException(e);
		}
	}

	public String decode(String token) {
		String subject = "";
		try {
			Claims claim = Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody();
			subject = claim.getSubject();
		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException(e);
		} catch (IllegalArgumentException e) {
			throw new JwtException(e);
		} catch (Exception e) {
			throw new JwtException(e);
		}
		return subject;
	}

}

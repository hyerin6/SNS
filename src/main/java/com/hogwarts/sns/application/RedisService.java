package com.hogwarts.sns.application;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

	private final static String EMPTY = "";

	private final RedisTemplate<String, String> redisTemplate;

	public String getValue(String key) {
		return redisTemplate.hasKey(key) ? redisTemplate.opsForValue().get(key) : EMPTY;
	}

	public void setAccessToken(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, 1, TimeUnit.DAYS);
	}

}

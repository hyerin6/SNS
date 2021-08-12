package com.hogwarts.sns.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntityConstants {
	public static final ResponseEntity CREATED = ResponseEntity.status(HttpStatus.CREATED).build();
	public static final ResponseEntity OK = ResponseEntity.status(HttpStatus.OK).build();
}

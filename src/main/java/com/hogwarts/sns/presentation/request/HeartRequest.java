package com.hogwarts.sns.presentation.request;

import com.hogwarts.sns.domain.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartRequest {
	private Type type;
	private Long parentId;
}

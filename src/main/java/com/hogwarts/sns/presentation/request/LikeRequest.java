package com.hogwarts.sns.presentation.request;

import com.hogwarts.sns.domain.Type;

import lombok.Getter;

@Getter
public class LikeRequest {
	private Type type;
	private Long parentId;
}

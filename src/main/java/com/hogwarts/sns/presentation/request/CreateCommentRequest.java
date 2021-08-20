package com.hogwarts.sns.presentation.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
	private String content;
	private Long postId;
}

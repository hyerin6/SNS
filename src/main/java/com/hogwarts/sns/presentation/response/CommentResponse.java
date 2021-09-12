package com.hogwarts.sns.presentation.response;

import com.hogwarts.sns.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
	private Comment comment;
	private int likeCnt;
}

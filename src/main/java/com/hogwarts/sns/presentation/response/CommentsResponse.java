package com.hogwarts.sns.presentation.response;

import java.util.List;

import com.hogwarts.sns.domain.Comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentsResponse {

	private List<Comment> comments;

}

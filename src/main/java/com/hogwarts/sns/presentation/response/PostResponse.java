package com.hogwarts.sns.presentation.response;

import java.util.List;

import com.hogwarts.sns.domain.Image;
import com.hogwarts.sns.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
	private Post post;
	private List<Image> images;
	private int heartCnt;
}

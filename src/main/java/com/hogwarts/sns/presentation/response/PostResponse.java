package com.hogwarts.sns.presentation.response;

import java.io.Serializable;
import java.util.List;

import com.hogwarts.sns.domain.Image;
import com.hogwarts.sns.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse implements Serializable {
	private Post post;
	private List<Image> images;
}


package com.hogwarts.sns.post.dto;

import java.io.Serializable;
import java.util.List;

import com.hogwarts.sns.image.domain.Image;
import com.hogwarts.sns.post.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse implements Serializable {
	private Post post;
	private List<Image> images;
}


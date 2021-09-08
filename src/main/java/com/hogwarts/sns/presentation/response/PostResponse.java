package com.hogwarts.sns.presentation.response;

import java.io.Serializable;
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
public class PostResponse implements Serializable {
	private Post post;
	private List<Image> images;
}


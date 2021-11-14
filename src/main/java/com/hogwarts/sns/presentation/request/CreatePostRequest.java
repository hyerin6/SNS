package com.hogwarts.sns.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class CreatePostRequest {
	private String content;
	// private List<MultipartFile> images;
}

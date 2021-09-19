package com.hogwarts.sns.presentation.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePostRequest {
	private String content;
	private List<MultipartFile> images;

}

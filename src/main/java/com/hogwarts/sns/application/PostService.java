package com.hogwarts.sns.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Image;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.exception.ResponseException;
import com.hogwarts.sns.exception.e4xx.NotFoundException;
import com.hogwarts.sns.persistence.PostRepository;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.ModifyPostRequest;
import com.hogwarts.sns.presentation.response.PostResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final ImageService imageService;

	@Transactional
	public void addPost(User user, CreatePostRequest request) {
		Post post = Post.builder()
			.user(user)
			.content(request.getContent())
			.build();

		postRepository.save(post);
		imageService.addImage(post, request.getImages());
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) throws ResponseException {
		Post post = postRepository.findById(id)
			.orElseThrow(NotFoundException.POST);

		List<Image> images = imageService.getImage(id);

		return PostResponse.builder()
			.post(post)
			.images(images)
			.build();
	}

	@Transactional
	public void modifyPost(Long id, ModifyPostRequest request) throws ResponseException {
		Post post = postRepository.findById(id)
			.orElseThrow(NotFoundException.POST);

		post.modifyContent(request.getContent());
	}

	@Transactional
	public void deletePost(Long id) {
		imageService.delete(id);
		postRepository.deleteById(id);
	}

}

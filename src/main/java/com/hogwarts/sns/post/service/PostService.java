package com.hogwarts.sns.post.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hogwarts.sns.image.domain.Image;
import com.hogwarts.sns.image.service.ImageService;
import com.hogwarts.sns.post.domain.Post;
import com.hogwarts.sns.post.dto.CreatePostRequest;
import com.hogwarts.sns.post.dto.ModifyPostRequest;
import com.hogwarts.sns.post.dto.PostResponse;
import com.hogwarts.sns.post.repository.PostRepository;
import com.hogwarts.sns.user.domain.User;

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

		if (request.getImages().size() == 1) {
			imageService.addImage(post, request.getImages().get(0));
		} else if (!request.getImages().isEmpty()) {
			imageService.addImages(post, request.getImages());
		}
	}

	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 게시물이 존재하지 않습니다."));

		List<Image> images = imageService.getImages(id);

		return PostResponse.builder()
			.post(post)
			.images(images)
			.build();
	}

	@Transactional
	public void modifyPost(Long id, ModifyPostRequest request) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 게시물이 존재하지 않습니다."));
		post.setContent(request.getContent());
	}

	@Transactional
	public void deletePost(Long id) {
		imageService.delete(id);
		postRepository.deleteById(id);
	}

}

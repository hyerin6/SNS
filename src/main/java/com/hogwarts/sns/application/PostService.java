package com.hogwarts.sns.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Image;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.PostRepository;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.exception.e4xx.NotFoundException;
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

	@Transactional(readOnly = true)
	public List<PostResponse> getPosts(Long userId, Long lastPostId, Pageable pageable) {
		List<Post> posts;

		if (lastPostId > Long.MIN_VALUE) {
			posts = postRepository.findByUserIdAndIdLessThan(userId, lastPostId, pageable);
		} else {
			posts = postRepository.findByUserId(userId, pageable);
		}

		List<PostResponse> postResponses = getImages(posts);

		return postResponses;
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getFeed(Long userId, Long lastPostId, Pageable pageable) {
		List<Post> posts;

		if (lastPostId > Long.MIN_VALUE) {
			posts = postRepository.findByLastIdAndJoinFollow(userId, lastPostId, pageable);
		} else {
			posts = postRepository.findByJoinFollow(userId, pageable);
		}

		List<PostResponse> postResponses = getImages(posts);

		return postResponses;
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

	private List<PostResponse> getImages(List<Post> posts) {
		List<PostResponse> postResponses = new ArrayList<>();

		for (Post post : posts) {
			List<Image> images = imageService.getImage(post.getId());
			PostResponse response = new PostResponse(post, images);
			postResponses.add(response);
		}

		return postResponses;
	}

}

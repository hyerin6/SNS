package com.hogwarts.sns.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.PostIndex;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.PostIndexRepository;
import com.hogwarts.sns.infrastructure.persistence.PostRepository;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.exception.e4xx.NotFoundException;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.ModifyPostRequest;
import com.hogwarts.sns.presentation.request.PostSearchRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final ImageService imageService;
	private final PostIndexRepository postIndexRepository;

	@Transactional
	public void create(User user, CreatePostRequest request) {
		Post post = Post.builder()
			.user(user)
			.content(request.getContent())
			.build();

		Post savedPost = postRepository.save(post);

		// imageService.create(post, request.getImages());

		// PostIndex postIndex = PostIndex.builder()
		// 	.id(String.valueOf(savedPost.getId()))
		// 	.content(request.getContent())
		// 	.createdAt(savedPost.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
		// 	.updatedAt(savedPost.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
		// 	.build();
		//
		// postIndexRepository.save(postIndex);
	}

	@Transactional(readOnly = true)
	public Post getPost(Long id) throws ResponseException {
		return postRepository.findById(id)
			.orElseThrow(NotFoundException.POST);
	}

	@Transactional(readOnly = true)
	public List<Post> getPosts(Long userId, Long lastPostId, Pageable pageable) {
		if (lastPostId > 0) {
			return postRepository.findByUserIdAndIdLessThan(userId, lastPostId, pageable);
		}

		return postRepository.findByUserId(userId, pageable);
	}

	@Transactional(readOnly = true)
	public List<Post> getFeed(Long userId, Long lastPostId, Pageable pageable) {
		if (lastPostId > 0) {
			return postRepository.findByJoinFollowAndLastIdLessThan(userId, lastPostId, pageable);
		}

		return postRepository.findByJoinFollow(userId, pageable);
	}

	@Transactional
	public void modify(Long id, ModifyPostRequest request) throws ResponseException {
		Post post = postRepository.findById(id)
			.orElseThrow(NotFoundException.POST);

		post.modifyContent(request.getContent());
	}

	@Transactional
	public void delete(Long id) {
		imageService.delete(id);
		postRepository.deleteById(id);
	}

	public List<PostIndex> getAllIndex(PostSearchRequest request, Pageable pageable) {
		return postIndexRepository.searchByContent(request.getKeyword(), pageable);
	}

}

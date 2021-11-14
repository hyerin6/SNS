package com.hogwarts.sns.presentation;

import static com.hogwarts.sns.presentation.response.ResponseEntityConstants.*;
import static com.hogwarts.sns.utils.constants.PagingConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hogwarts.sns.application.HeartService;
import com.hogwarts.sns.application.ImageService;
import com.hogwarts.sns.application.PostService;
import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.Image;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.PostIndex;
import com.hogwarts.sns.domain.Type;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.security.AuthenticationPrincipal;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.request.CreatePostRequest;
import com.hogwarts.sns.presentation.request.HeartRequest;
import com.hogwarts.sns.presentation.request.ModifyPostRequest;
import com.hogwarts.sns.presentation.request.PostSearchRequest;
import com.hogwarts.sns.presentation.request.PostsRequest;
import com.hogwarts.sns.presentation.response.PostResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

	private final PostService postService;
	private final ImageService imageService;
	private final HeartService heartService;
	private final UserService userService;

	@PostMapping("/post")
	public ResponseEntity<Void> create(@AuthenticationPrincipal String userId,
		@ModelAttribute CreatePostRequest request) throws JsonProcessingException {
		User user = userService.getUser(userId);
		postService.create(user, request);
		return CREATED;
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<PostResponse> getContent(@PathVariable Long id) throws ResponseException {
		Post post = postService.getPost(id);
		List<Image> images = imageService.getImage(post.getId());
		int heartCnt = heartService.getHeartCnt(new HeartRequest(Type.POST, post.getId()));

		PostResponse postResponse = PostResponse.builder()
			.post(post)
			.images(images)
			.heartCnt(heartCnt)
			.build();

		return ResponseEntity.ok(postResponse);
	}

	@PutMapping("/post/{id}")
	public ResponseEntity<Void> modify(@PathVariable Long id, @RequestBody ModifyPostRequest request) throws
		ResponseException {
		postService.modify(id, request);
		return OK;
	}

	@DeleteMapping("/post/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		postService.delete(id);
		return OK;
	}

	@PostMapping("/posts")
	public ResponseEntity<List<PostResponse>> getMyPosts(@AuthenticationPrincipal String userId,
		@RequestBody PostsRequest request) {
		User user = userService.getUser(userId);
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY).descending());

		List<Post> posts = postService.getPosts(user.getId(), request.getLastPostId(), pageRequest);

		List<PostResponse> postResponses = new ArrayList<>();
		for (Post post : posts) {
			List<Image> images = imageService.getImage(post.getId());
			HeartRequest heartRequest = new HeartRequest(Type.POST, post.getId());
			int heartCnt = heartService.getHeartCnt(heartRequest);
			PostResponse response = new PostResponse(post, images, heartCnt);
			postResponses.add(response);
		}

		return ResponseEntity.ok(postResponses);
	}

	@PostMapping("/posts/{userId}")
	public ResponseEntity<List<PostResponse>> getPosts(@PathVariable("userId") Long userId,
		@RequestBody PostsRequest request) {
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY).descending());

		List<Post> posts = postService.getPosts(userId, request.getLastPostId(), pageRequest);

		List<PostResponse> postResponses = new ArrayList<>();
		for (Post post : posts) {
			List<Image> images = imageService.getImage(post.getId());
			HeartRequest heartRequest = new HeartRequest(Type.POST, post.getId());
			int heartCnt = heartService.getHeartCnt(heartRequest);
			PostResponse response = new PostResponse(post, images, heartCnt);
			postResponses.add(response);
		}

		return ResponseEntity.ok(postResponses);
	}

	@PostMapping("/feed")
	public ResponseEntity<List<PostResponse>> getFeed(@AuthenticationPrincipal String userId,
		@RequestBody PostsRequest request) {
		User user = userService.getUser(userId);
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY).descending());

		List<Post> posts = postService.getFeed(user.getId(), request.getLastPostId(), pageRequest);

		List<PostResponse> postResponses = new ArrayList<>();
		for (Post post : posts) {
			List<Image> images = imageService.getImage(post.getId());
			HeartRequest heartRequest = new HeartRequest(Type.POST, post.getId());
			int heartCnt = heartService.getHeartCnt(heartRequest);
			PostResponse response = new PostResponse(post, images, heartCnt);
			postResponses.add(response);
		}

		return ResponseEntity.ok(postResponses);
	}

	@PostMapping("/posts/search")
	public ResponseEntity<List<PostResponse>> search(@RequestBody PostSearchRequest searchRequest) {
		PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by(SORT_PROPERTY).descending());
		List<PostIndex> postIndices = postService.getAllIndex(searchRequest, pageRequest);

		List<PostResponse> postResponses = new ArrayList<>();
		for (PostIndex postIndex : postIndices) {
			List<Image> images = imageService.getImage(Long.valueOf(postIndex.getId()));
			HeartRequest heartRequest = new HeartRequest(Type.POST, Long.valueOf(postIndex.getId()));
			int hearCnt = heartService.getHeartCnt(heartRequest);
			Post post = Post.builder()
				.id(Long.valueOf(postIndex.getId()))
				.content(postIndex.getContent())
				.createdAt(postIndex.getCreatedAt().toLocalDateTime())
				.updatedAt(postIndex.getUpdatedAt().toLocalDateTime())
				.build();
			PostResponse response = new PostResponse(post, images, hearCnt);
			postResponses.add(response);
		}

		return ResponseEntity.ok(postResponses);
	}
}

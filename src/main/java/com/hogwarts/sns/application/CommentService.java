package com.hogwarts.sns.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Comment;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.Type;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.CommentRepository;
import com.hogwarts.sns.presentation.exception.ResponseException;
import com.hogwarts.sns.presentation.exception.e4xx.NotFoundException;
import com.hogwarts.sns.presentation.request.CreateCommentRequest;
import com.hogwarts.sns.presentation.request.LikeRequest;
import com.hogwarts.sns.presentation.response.CommentResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final LikeService likeService;

	@Transactional
	public void create(User user, Post post, CreateCommentRequest request) {
		Comment comment = Comment.builder()
			.content(request.getContent())
			.post(post)
			.user(user)
			.build();

		commentRepository.save(comment);
	}

	@Transactional
	public void delete(Long id) throws ResponseException {
		Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException.COMMENT);
		commentRepository.delete(comment);
	}

	@Transactional(readOnly = true)
	public List<CommentResponse> getComments(Long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		List<CommentResponse> commentResponses = new ArrayList<>();
		
		for (Comment comment : comments) {
			int likeCnt = likeService.getLikeCnt(new LikeRequest(Type.COMMENT, comment.getId()));
			commentResponses.add(new CommentResponse(comment, likeCnt));
		}

		return commentResponses;
	}

}

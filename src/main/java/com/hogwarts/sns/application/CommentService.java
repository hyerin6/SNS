package com.hogwarts.sns.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Comment;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.exception.ResponseException;
import com.hogwarts.sns.exception.e4xx.NotFoundException;
import com.hogwarts.sns.persistence.CommentRepository;
import com.hogwarts.sns.presentation.request.CreateCommentRequest;
import com.hogwarts.sns.presentation.response.CommentsResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

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
	public CommentsResponse getComments(Long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return new CommentsResponse(comments);
	}

}

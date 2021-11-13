package com.hogwarts.sns.application;

import java.time.ZoneId;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwarts.sns.domain.Post;
import com.hogwarts.sns.domain.PostIndex;
import com.hogwarts.sns.infrastructure.persistence.PostIndexRepository;
import com.hogwarts.sns.infrastructure.persistence.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Consumer {
	
	private final ObjectMapper objectMapper;

	private final PostRepository postRepository;

	private final PostIndexRepository postIndexRepository;

	@RabbitListener(queues = "CREATE_POST_QUEUE")
	public void handler(String message) throws JsonProcessingException {
		Post post = objectMapper.readValue(message, Post.class);
		Post savedPost = postRepository.save(post);
		PostIndex postIndex = PostIndex.builder()
			.id(String.valueOf(savedPost.getId()))
			.content(savedPost.getContent())
			.createdAt(savedPost.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
			.updatedAt(savedPost.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
			.build();
		postIndexRepository.save(postIndex);
	}

}

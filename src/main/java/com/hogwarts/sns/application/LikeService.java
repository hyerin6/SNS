package com.hogwarts.sns.application;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hogwarts.sns.domain.Like;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.LikeRepository;
import com.hogwarts.sns.presentation.request.LikeRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikeService {

	private final LikeRepository likeRepository;

	@Transactional
	public void Like(User user, LikeRequest request) {
		Like like = Like.builder()
			.user(user)
			.type(request.getType())
			.parentId(request.getParentId())
			.build();

		likeRepository.save(like);
	}

	@Transactional
	public void unLike(Long id) {
		likeRepository.deleteById(id);
	}

}

package com.hogwarts.sns.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hogwarts.sns.domain.Heart;
import com.hogwarts.sns.domain.User;
import com.hogwarts.sns.infrastructure.persistence.HeartRepository;
import com.hogwarts.sns.presentation.request.HeartRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HeartService {

	private final HeartRepository heartRepository;

	@Transactional
	public void create(User user, HeartRequest request) {
		Heart like = Heart.builder()
			.user(user)
			.type(request.getType())
			.parentId(request.getParentId())
			.build();

		heartRepository.save(like);
	}

	@Transactional
	public void delete(Long id) {
		heartRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public int getHeartCnt(HeartRequest heartRequest) {
		return heartRepository.countByTypeAndParentId(heartRequest.getType(), heartRequest.getParentId());
	}
}

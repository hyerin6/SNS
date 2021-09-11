package com.hogwarts.sns.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	void findByUserId() {
		postRepository.findByUserId(1L, PageRequest.of(0, 5, Sort.by("id").descending()));
	}

	@Test
	void findByUserIdAndIdLessThan() {
		postRepository.findByUserIdAndIdLessThan(1L, 100L, PageRequest.of(0, 5, Sort.by("id").descending()));
	}

	@Test
	void findByJoinFollow() {
		postRepository.findByJoinFollow(1L, PageRequest.of(0, 5, Sort.by("id").descending()));
	}

	@Test
	void findByLastIdAndJoinFollow() {
		postRepository.findByLastIdAndJoinFollow(1L, 100L, PageRequest.of(0, 5, Sort.by("id").descending()));
	}
}
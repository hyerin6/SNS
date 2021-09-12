package com.hogwarts.sns.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hogwarts.sns.domain.Type;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class LikeRepositoryTest {

	@Autowired
	private LikeRepository likeRepository;

	@Test
	void countByTypeAndParentId() {
		likeRepository.countByTypeAndParentId(Type.POST.toDbValue(), 1L);
	}
}
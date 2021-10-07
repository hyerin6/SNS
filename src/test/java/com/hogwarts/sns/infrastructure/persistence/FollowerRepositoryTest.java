package com.hogwarts.sns.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class FollowerRepositoryTest {
    @Autowired
    private FollowRepository followRepository;

    @Test
    public void test() {
        followRepository.findByFollower(1L, PageRequest.of(0, 5, Sort.by("id").descending()));
        followRepository.findByFollowing(1L, PageRequest.of(0, 5, Sort.by("id").descending()));
    }
}

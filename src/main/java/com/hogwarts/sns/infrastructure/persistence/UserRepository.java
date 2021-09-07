package com.hogwarts.sns.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);
}

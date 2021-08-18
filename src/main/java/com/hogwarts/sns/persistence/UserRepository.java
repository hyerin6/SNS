package com.hogwarts.sns.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

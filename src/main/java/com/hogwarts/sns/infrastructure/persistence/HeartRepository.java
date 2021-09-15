package com.hogwarts.sns.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Heart;
import com.hogwarts.sns.domain.Type;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

	int countByTypeAndParentId(@Param("type") Type type, @Param("parentId") Long parentId);

}

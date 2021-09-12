package com.hogwarts.sns.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query(value = "SELECT COUNT(l.id)"
		+ " FROM `like` l"
		+ " WHERE l.type = :type AND l.parent_id = :parentId", nativeQuery = true)
	int countByTypeAndParentId(int type, Long parentId);

}

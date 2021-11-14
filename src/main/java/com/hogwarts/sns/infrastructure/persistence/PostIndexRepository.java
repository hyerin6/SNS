package com.hogwarts.sns.infrastructure.persistence;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.hogwarts.sns.domain.PostIndex;

@Repository
public interface PostIndexRepository extends ElasticsearchRepository<PostIndex, Long> {

	@Query("{\"match\": {\"content\": \"?0\"}}")
	List<PostIndex> searchByContent(String content);

}

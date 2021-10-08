package com.hogwarts.sns.infrastructure.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories("com.hogwarts.sns.infrastructure.persistence")
@Configuration
public class ElasticSearchConfig {

	@Value("${spring.elasticsearch.rest.uris}")
	String elasticHost;

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(RestClient.builder(HttpHost.create(elasticHost)));
	}

}

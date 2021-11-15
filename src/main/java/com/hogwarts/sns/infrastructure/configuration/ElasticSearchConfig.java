package com.hogwarts.sns.infrastructure.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories("com.hogwarts.sns.infrastructure.persistence")
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

	@Value("${spring.elasticsearch.rest.uris}")
	String elasticHost;

	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {
		return new RestHighLevelClient(RestClient.builder(HttpHost.create(elasticHost)));
	}
}

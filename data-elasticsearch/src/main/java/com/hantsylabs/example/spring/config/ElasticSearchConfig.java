package com.hantsylabs.example.spring.config;

import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan(basePackages = { "com.hantsylabs.example.spring.search" })
@EnableElasticsearchRepositories({ "com.hantsylabs.example.spring.search" })
public class ElasticSearchConfig {

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(NodeBuilder.nodeBuilder().local(true).node().client());
	}

}

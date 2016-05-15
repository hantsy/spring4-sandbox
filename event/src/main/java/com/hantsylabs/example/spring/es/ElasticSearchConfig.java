package com.hantsylabs.example.spring.es;


import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan(basePackages = { "com.hantsylabs.example.spring.es" } )
@EnableElasticsearchRepositories( { "com.hantsylabs.example.spring.es" })
public class ElasticSearchConfig {

	
	@Bean
    public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(NodeBuilder.nodeBuilder().local(true).node()
				.client());
    }
	

}

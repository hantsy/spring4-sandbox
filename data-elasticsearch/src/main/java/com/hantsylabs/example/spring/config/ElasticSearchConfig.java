package com.hantsylabs.example.spring.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan(basePackages = { "com.hantsylabs.example.spring.search" } )
@EnableElasticsearchRepositories( { "com.hantsylabs.example.spring.search" })
public class ElasticSearchConfig {
	@Bean
	public Client client(){
		
		NodeClient client = null;
		try {
			client = new NodeClientFactoryBean(true).getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return client;
	}
	
	@Bean 
	public ElasticsearchTemplate elasticsearchTempalte(Client client){
		return new ElasticsearchTemplate(client);
	}

}

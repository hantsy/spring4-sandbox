/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.hantsylabs.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 *
 * @author hantsy
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.hantsylabs.example.spring.repository")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "conference-db";
	}

	@Override
	public Mongo mongo() throws Exception {
		Mongo mongo = new MongoClient("localhost");
		return mongo;
	}

	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}

	@Bean
	public LoggingEventListener loggingEventListener() {
		return new LoggingEventListener();
	}

}

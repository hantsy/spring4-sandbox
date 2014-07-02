package com.hantsylabs.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.hantsylabs.example.spring.mongo.ConferenceRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages="com.hantsylabs.example.spring")
@EnableMongoRepositories(basePackageClasses = ConferenceRepository.class)
@EnableMongoAuditing(modifyOnCreate=false)
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
	public LoggingEventListener logginEventListener(){
		return new LoggingEventListener();
	}

}

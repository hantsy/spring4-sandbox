package com.hantsylabs.example.spring.mongo;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;

@Configuration
@ComponentScan(basePackages = "com.hantsylabs.example.spring")
@EnableMongoRepositories(basePackages = "com.hantsylabs.example.spring")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "conference-db";
	}

	// @Override
	// public Mongo mongo() throws Exception {
	// return new MongoClient("localhost");
	// }
	@Bean(destroyMethod = "close")
	public Mongo mongo() throws IOException {
		return new EmbeddedMongoBuilder().version("2.6.1").bindIp("127.0.0.1").port(12345).build();
	}

	@Bean
	public MongoExceptionTranslator exceptionTranslator() {
		return new MongoExceptionTranslator();
	}

	@Bean
	public LoggingEventListener logginEventListener() {
		return new LoggingEventListener();
	}

}

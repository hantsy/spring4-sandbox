package com.hantsylabs.example.spring.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepository extends
		MongoRepository<Conference, String> {
	
	public Conference findByName(String name);

}

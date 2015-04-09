package com.hantsylabs.example.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;


@Repository
public interface ConferenceRepository extends 
		MongoRepository<Conference, Long> {

}

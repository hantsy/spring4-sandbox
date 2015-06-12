package com.hantsylabs.example.spring.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepository extends 
		CrudRepository<Conference, Long>,
		QueryDslPredicateExecutor<Conference> {

	public Conference findBySlug(String slug);

}

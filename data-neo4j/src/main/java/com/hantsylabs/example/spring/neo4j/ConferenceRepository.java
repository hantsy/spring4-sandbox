package com.hantsylabs.example.spring.neo4j;


import org.springframework.data.neo4j.repository.GraphRepository;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepository extends GraphRepository<Conference>  {

	Conference findBySlug(String slug);
		
}

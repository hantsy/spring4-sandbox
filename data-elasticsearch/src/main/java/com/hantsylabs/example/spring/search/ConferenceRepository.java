package com.hantsylabs.example.spring.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepository
		extends ElasticsearchRepository<Conference, Long>{

	Conference findBySlug(String slug);

}

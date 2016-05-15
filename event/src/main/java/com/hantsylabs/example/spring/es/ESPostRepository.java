package com.hantsylabs.example.spring.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESPostRepository
		extends ElasticsearchRepository<ESPost, String>{


}

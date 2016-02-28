package com.hantsylabs.example.spring.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.hantsylabs.example.spring.model.Post;

public interface PostRepository
		extends ElasticsearchRepository<Post, Long>{

	Post findBySlug(String slug);

}

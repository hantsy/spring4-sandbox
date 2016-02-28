package com.hantsylabs.example.spring.neo4j;


import org.springframework.data.neo4j.repository.GraphRepository;

import com.hantsylabs.example.spring.model.Post;

public interface PostRepository extends GraphRepository<Post>  {

	Post findBySlug(String slug);
		
}

package com.hantsylabs.example.spring.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.hantsylabs.example.spring.model.Post;

public interface PostRepository extends 
		CrudRepository<Post, Long>,
		QueryDslPredicateExecutor<Post> {

	public Post findBySlug(String slug);

}

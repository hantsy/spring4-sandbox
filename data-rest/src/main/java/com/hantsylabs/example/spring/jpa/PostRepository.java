package com.hantsylabs.example.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hantsylabs.example.spring.model.Post;

@RepositoryRestResource(collectionResourceRel = "posts", path = "/posts")
public interface PostRepository extends JpaRepository<Post, Long> {
	Post findBySlug(String slug);
}

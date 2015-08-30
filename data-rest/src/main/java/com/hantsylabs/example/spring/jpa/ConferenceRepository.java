package com.hantsylabs.example.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.hantsylabs.example.spring.model.Conference;

@RepositoryRestResource(collectionResourceRel = "conferences", path = "/conferences")
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

	Conference findBySlug(String slug);

}

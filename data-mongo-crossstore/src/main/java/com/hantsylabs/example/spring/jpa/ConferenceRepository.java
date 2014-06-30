package com.hantsylabs.example.spring.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;


@Repository
public interface ConferenceRepository extends 
		JpaRepository<Conference, Long> {

}

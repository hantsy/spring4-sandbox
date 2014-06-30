package com.hantsylabs.example.spring.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;

@Repository
public interface ConferenceRepository extends
		JpaRepository<Conference, Long>{

	Conference findBySlug(String slug);

}

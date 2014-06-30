package com.hantsylabs.example.spring.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.Signup;

@Repository
public interface SignupRepository extends
		JpaRepository<Signup, Long>{

	Signup findByConference(Conference conference);

	Signup findById(Long id);

}

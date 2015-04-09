package com.hantsylabs.example.spring.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Signup;


@Repository
public interface SignupRepository extends 
		MongoRepository<Signup, Long> {

}

package com.hantsylabs.example.spring.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.DelegatingSpringDataMongodbQuery;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Signup;

@Repository
public class MongodbQuerySignupRepository {

	private DelegatingSpringDataMongodbQuery<Signup> query;
	
	@Autowired
	public MongodbQuerySignupRepository(MongoTemplate mongoTemplate){
		this.query=new DelegatingSpringDataMongodbQuery<Signup>(mongoTemplate, Signup.class);		
	}
	
	public DelegatingSpringDataMongodbQuery<Signup> query(){
		return this.query;
	}
	
}

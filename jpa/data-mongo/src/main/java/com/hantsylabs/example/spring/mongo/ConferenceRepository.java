package com.hantsylabs.example.spring.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;

@Repository
public interface ConferenceRepository extends ConferenceRepositoryCustom, MongoRepository<Conference, String>
,		QueryDslPredicateExecutor<Conference> 
{
	
	public Conference findBySlug(String slug);

	public List<Conference> findByAddressCountry(String country);
	
	@Query("{description:{ $regex: '*'+?0+'*', $options: 'i'}}")
	public List<Conference> searchByDescriptionLike(String like);
	
	public List<Conference> findByDescriptionLike(String like);
	
	public List<Conference> findByDescriptionRegex(String like);
}

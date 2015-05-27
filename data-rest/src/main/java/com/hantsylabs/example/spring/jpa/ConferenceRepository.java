package com.hantsylabs.example.spring.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;

@RepositoryRestResource(collectionResourceRel="conferences", path="/conferences")
public interface ConferenceRepository extends ConferenceRepositoryCustom, 
		JpaRepository<Conference, Long>,
		JpaSpecificationExecutor<Conference>, 
		QueryDslPredicateExecutor<Conference>  {

	Conference findBySlug(String slug);
	
	@Query("from Conference where name=?1")
	public List<Conference> searchByConferenceName(String name);

	@Query("from Conference where name=:name")
	public List<Conference> searchByNamedConferenceName(@Param("name") String name);

	@Query
	public List<Conference> searchByMyNamedQuery(String name);

	public List<Conference> findByAddressCountry(String country);
	
	public List<Conference> findByDescriptionLike(String desc);
	
	@Query("update Conference conf set conf.description=?1 where conf.id=?2 ")
	@Modifying
	public void modifyConferenceDescrition(String description, Long id);

}

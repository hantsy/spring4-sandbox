package com.hantsylabs.example.spring.dao;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceDao {

	public abstract Conference findById(Long id);

	public abstract Long save(Conference conference);

	public abstract void delete(Long id);
	
	public abstract void delete(Conference obj);

	public abstract void update(Conference conference);

	public abstract void deleteAll();

	public abstract Conference findBySlug(String string);

}

package com.hantsylabs.example.spring.repository;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepository {

	public abstract Conference findById(Long id);

	public abstract Conference save(Conference conference);
	
	public void delete(final Long id);

	public abstract void delete(Conference obj);

	public abstract void deleteAll();

	public abstract Conference findBySlug(String string);

}

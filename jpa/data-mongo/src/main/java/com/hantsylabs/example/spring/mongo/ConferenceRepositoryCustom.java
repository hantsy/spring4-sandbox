package com.hantsylabs.example.spring.mongo;

import java.util.List;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepositoryCustom {
	public List<Conference> searchByDescription(String like);
	
	public void updateConferenceDescription(String description, String id );

}

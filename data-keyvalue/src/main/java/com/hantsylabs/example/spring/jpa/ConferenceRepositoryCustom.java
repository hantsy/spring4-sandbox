package com.hantsylabs.example.spring.jpa;

import java.util.List;

import com.hantsylabs.example.spring.model.Conference;

public interface ConferenceRepositoryCustom {
	List<Conference> searchByDescription(String like);
}

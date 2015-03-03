package com.hantsylabs.example.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hantsylabs.example.spring.model.Conference;

//@Service
public class ConferenceService {

	@Autowired
	List<Conference> confs;

	public List<Conference> availableConferences() {
		return this.confs;
	}

}

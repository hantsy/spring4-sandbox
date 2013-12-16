package com.hantsylabs.example.spring.service

import com.hantsylabs.example.spring.dao.ConferenceDao


class ConferenceService {
	
	def conferenceDao
	
	def findConferenceBySlug(String slug) {
		conferenceDao.findBySlug(slug)
	}
	
}

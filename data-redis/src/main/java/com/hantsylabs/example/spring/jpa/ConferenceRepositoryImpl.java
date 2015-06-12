package com.hantsylabs.example.spring.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hantsylabs.example.spring.model.Conference;

public class ConferenceRepositoryImpl implements ConferenceRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Conference> searchByDescription(String d) {
		return em.createQuery("from Conference where description like :description", Conference.class)
		.setParameter("description", "%"+d+"%")
		.getResultList();
	}

}

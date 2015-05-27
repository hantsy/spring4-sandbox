package com.hantsylabs.example.spring.repository;

import java.util.List;

import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Conference;

@Repository
public class JpaConferenceRepositoryImpl implements ConferenceRepository {
	private static final Logger log = LoggerFactory
			.getLogger(JpaConferenceRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@CacheResult(cacheName = "conference")
	public Conference findById(Long id) {
		return (Conference) entityManager.find(Conference.class, id);

	}

	@Override
	@CachePut(cacheName = "conference" )
	public Conference save(final Conference conference) {
		if (conference.isNew()) {
			entityManager.persist(conference);
			entityManager.flush();
			return conference;
		} else {
			Conference conf = entityManager.merge(conference);
			entityManager.flush();
			return conf;
		}
	}

	
	@Override
	@CacheRemove(cacheName = "conference" )
	public void delete(final Long id) {
		entityManager.remove(entityManager.find(Conference.class, id));
		entityManager.flush();
	}
	
	@Override
	@CacheRemove(cacheName = "conference" )
	public void delete(final Conference conf) {
		entityManager.remove(entityManager.merge(conf));
		entityManager.flush();
	}

	@Override
	@CacheRemoveAll(cacheName = "conference")
	public void deleteAll() {
		List<Conference> all = entityManager.createQuery("from Conference",
				Conference.class).getResultList();
		for (Conference c : all) {
			delete(c);
		}
		entityManager.flush();
	}

	@Override
	@CacheResult(cacheName = "conference")
	public Conference findBySlug(String slug) {
		List<Conference> all = entityManager
				.createQuery("from Conference where slug=:slug",
						Conference.class).setParameter("slug", slug)
				.getResultList();
		if (!all.isEmpty()) {
			return all.get(0); 
		}

		return null;
	}
}

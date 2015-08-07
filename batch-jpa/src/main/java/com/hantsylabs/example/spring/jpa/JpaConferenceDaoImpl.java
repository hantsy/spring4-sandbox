package com.hantsylabs.example.spring.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.dao.ConferenceDao;
import com.hantsylabs.example.spring.model.Conference;

@Repository
@Transactional(transactionManager="transactionManager")
public class JpaConferenceDaoImpl implements ConferenceDao {
	private static final Logger log = LoggerFactory
			.getLogger(JpaConferenceDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Conference findById(Long id) {
		return (Conference) entityManager.find(Conference.class, id);

	}

	@Override
	public Long save(final Conference conference) {
		entityManager.persist(conference);
		return conference.getId();
	}

	@Override
	public void update(final Conference conference) {
		entityManager.merge(conference);
	}

	@Override
	public void delete(final Long id) {
		entityManager.remove(findById(id));
	}

	@Override
	public void delete(final Conference conf) {
		entityManager.remove(entityManager.merge(conf));
	}

	@Override
	public void deleteAll() {
		List<Conference> all = entityManager.createQuery("from Conference",
				Conference.class).getResultList();
		for (Conference c : all) {
			delete(c);
		}
	}

	@Override
	public Conference findBySlug(String slug) {
		List<Conference> all = entityManager
				.createQuery("from Conference where slug=:slug", Conference.class)
				.setParameter("slug", slug).getResultList();
		if (!all.isEmpty()) {
			return all.get(0);
		}

		return null;
	}
}

package com.hantsylabs.example.spring.hibernate5;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.dao.ConferenceDao;
import com.hantsylabs.example.spring.model.Conference;

@Repository
public class Hibernate5ConferenceDaoImpl implements ConferenceDao {
	private static final Logger log = LoggerFactory
			.getLogger(Hibernate5ConferenceDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Conference findById(Long id) {
		return (Conference) session().load(Conference.class, id);

	}

	@Override
	public Long save(final Conference conference) {
		return (Long) session().save(conference);
	}

	@Override
	public void update(final Conference conference) {
		session().update(conference);
	}

	@Override
	public void delete(final Long id) {
		session().delete(session().get(Conference.class, id));
	}

	@Override
	public void delete(final Conference conf) {
		session().refresh(conf);
		session().delete(conf);
	}

	@Override
	public void deleteAll() {
		List<Conference> all = session().createQuery("from Conference").list();
		for (Conference c : all) {
			delete(c);
		}
	}

	@Override
	public Conference findBySlug(String slug) {
		List<Conference> all = session().createQuery("from Conference where slug=:slug").setParameter("slug", slug).list();
		if(!all.isEmpty()){
			return all.get(0);
		}
		
		return null;
	}
}

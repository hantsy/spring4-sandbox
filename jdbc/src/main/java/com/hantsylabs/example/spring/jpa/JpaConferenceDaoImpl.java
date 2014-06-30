package com.hantsylabs.example.spring.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.dao.ConferenceDao;
import com.hantsylabs.example.spring.model.Conference;

@Repository
public class JpaConferenceDaoImpl extends JpaDaoSupport implements
		ConferenceDao {
	private static final Logger log = LoggerFactory
			.getLogger(JpaConferenceDaoImpl.class);

	@Autowired
	public JpaConferenceDaoImpl(EntityManagerFactory emf) {
		super();
		setEntityManagerFactory(emf);
	}

	@Override
	public Conference findById(Long id) {
		return (Conference) getJpaTemplate().find(Conference.class, id);

	}

	@Override
	public Long save(final Conference conference) {
		getJpaTemplate().persist(conference);
		return conference.getId();
	}

	@Override
	public void update(final Conference conference) {
		getJpaTemplate().merge(conference);
	}

	@Override
	public void delete(final Long id) {
		getJpaTemplate().remove(getJpaTemplate().find(Conference.class, id));
	}

	@Override
	public void delete(final Conference conf) {
		getJpaTemplate().remove(getJpaTemplate().merge(conf));
	}

	@Override
	public void deleteAll() {
		List<Conference> all = getJpaTemplate().find("from Conference");
				
		for (Conference c : all) {
			delete(c);
		}
	}

	@Override
	public Conference findBySlug(String slug) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("slug", slug);
		
		List<Conference> all = getJpaTemplate()
				.findByNamedParams("from Conference where slug=:slug", params);
		if (!all.isEmpty()) {
			return all.get(0);
		}

		return null;
	}
}

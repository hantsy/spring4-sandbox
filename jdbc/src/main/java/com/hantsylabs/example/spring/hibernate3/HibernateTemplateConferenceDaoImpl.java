package com.hantsylabs.example.spring.hibernate3;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.dao.ConferenceDao;
import com.hantsylabs.example.spring.model.Conference;

@Repository
public class HibernateTemplateConferenceDaoImpl implements ConferenceDao {
	private static final Logger log = LoggerFactory
			.getLogger(HibernateTemplateConferenceDaoImpl.class);

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public Conference findById(Long id) {
		return hibernateTemplate.load(Conference.class, id);

	}

	@Override
	public Long save(final Conference conference) {
		return (Long) hibernateTemplate.save(conference);
	}

	@Override
	public void update(final Conference conference) {
		hibernateTemplate.update(conference);
	}

	@Override
	public void delete(final Long id) {
		hibernateTemplate.delete(
				hibernateTemplate.get(Conference.class, id));
	}

	@Override
	public void delete(final Conference conf) {
		hibernateTemplate.delete(conf);
	}

	@Override
	public void deleteAll() {
		Integer deleted = hibernateTemplate.execute(
				new HibernateCallback<Integer>() {

					@Override
					public Integer doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createQuery("delete from Conference")
								.executeUpdate();
					}
				});
		log.debug("deleted@" + deleted);
	}

	@Override
	public Conference findBySlug(final String slug) {
		List<Conference> result = hibernateTemplate.executeFind(
				new HibernateCallback<List<Conference>>() {

					@Override
					public List<Conference> doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session
								.createQuery("from Conference where slug=:slug")
								.setParameter("slug", slug).list();
					}
				});

		if (!result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}
}

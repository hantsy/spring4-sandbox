package com.hantsylabs.example.spring.repository;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.config.EhCacheJCacheConfig;
import com.hantsylabs.example.spring.config.JpaConfig;
import com.hantsylabs.example.spring.model.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfig.class, //
		EhCacheJCacheConfig.class //
})
public class ConferenceRepositoryImplTest {

	private static final Logger log = LoggerFactory
			.getLogger(ConferenceRepositoryImplTest.class);

	@Autowired
	ConferenceRepository conferenceDao;

	@PersistenceContext
	EntityManager entityManager;

	@BeforeClass
	public static void initTestClass() {
		log.debug("===================before class======================");

	}

	@Before
	@Transactional
	public void beforeTestCase() {
		log.debug("===================before test=====================");

		conferenceDao.deleteAll();
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("===================after test=====================");

	}

	private Conference newConference() {
		Conference conf = new Conference();
		conf.setName("JUD2013");
		conf.setSlug("jud-2013");
		conf.setDescription("JBoss User Developer Conference 2013 Boston");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 30);

		Date startedDate = cal.getTime();

		conf.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		Date endedDate = cal.getTime();
		conf.setEndedDate(endedDate);

		log.debug("new conference object:" + conf);
		return conf;
	}

	@Test
	@Transactional
	public void retrieveConference() {

		Conference conf = conferenceDao.save(newConference());

		entityManager.flush();
		assertTrue(conf != null);
		Long id = conf.getId();
		log.debug("id @=" + id);
		log.debug("@@@@@@@@@@@@@@query by id@@@@@@@@@@@@@@@@@@@@");
		Conference conference = conferenceDao.findById(id);

		assertTrue(conference != null);

		assertTrue("JUD2013".equals(conference.getName()));
		
		log.debug("@@@@@@@@@@@@@@upated@@@@@@@@@@@@@@@@@@@@");
		
		conference.setDescription("Updated Desc");
		conference = conferenceDao.save(conference);

		log.debug("@@@@@@@@@@@@@@loading from cache@@@@@@@@@@@@@@@@@@@@");



		Conference conference2 = conferenceDao.findById(id);
		assertTrue(conference2 != null);

		log.debug("@@@@@@@@@@@@@@query by slug@@@@@@@@@@@@@@@@@@@@");
		// query by slug
		conference = conferenceDao.findBySlug("jud-2013");

		assertTrue(conference != null);

		assertTrue("JUD2013".equals(conference.getName()));
	}

}

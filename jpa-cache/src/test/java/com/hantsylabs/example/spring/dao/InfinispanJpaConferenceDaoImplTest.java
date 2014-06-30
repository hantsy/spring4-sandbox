package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.AfterClass;
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

import com.hantsylabs.example.spring.model.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/hantsylabs/example/spring/config/applicationContext-jpa-infinispan.xml")
public class InfinispanJpaConferenceDaoImplTest {

	private static final Logger log = LoggerFactory
			.getLogger(InfinispanJpaConferenceDaoImplTest.class);

	@Autowired
	ConferenceDao conferenceDao;

	@PersistenceContext
	EntityManager entityManager;

	@BeforeClass
	public static void beforeClass() {
		log.debug("===================before class======================");
	}

	@AfterClass
	public static void afterClass() {
		log.debug("===================after class======================");
	}

	@Before
	@Transactional
	public void beforeTestCase() {
		log.debug("===================before test=====================");
		Long id = conferenceDao.save(newConference());
		assertTrue(id != null);
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("===================after test=====================");
		conferenceDao.deleteAll();
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

		log.debug("============beginning of calling retrieveConference===========");
		final Long id = 1L;

		Conference conference = conferenceDao.findById(id);

		assertTrue(conference != null);

		assertTrue("JUD2013".equals(conference.getName()));

		Conference conference2 = conferenceDao.findById(id);

		assertTrue(conference == conference2);

		log.debug("============end of calling retrieveConference===========");
	}

}

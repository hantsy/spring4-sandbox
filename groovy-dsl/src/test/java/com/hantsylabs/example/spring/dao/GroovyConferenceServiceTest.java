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
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractGenericContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.dao.GroovyConferenceServiceTest.GenericGroovyContextLoader;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.service.ConferenceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/hantsylabs/example/spring/config/JpaConfigGroovy.groovy", loader = GenericGroovyContextLoader.class)
@TransactionConfiguration
public class GroovyConferenceServiceTest {

	public static class GenericGroovyContextLoader extends
			AbstractGenericContextLoader {

		@Override
		protected BeanDefinitionReader createBeanDefinitionReader(
				GenericApplicationContext context) {
			return new GroovyBeanDefinitionReader(context);
		}

		@Override
		protected String getResourceSuffix() {
			return ".groovy";
		}

	}

	private static final Logger log = LoggerFactory
			.getLogger(GroovyConferenceServiceTest.class);

	@Autowired
	ConferenceDao conferenceDao;
	
	@Autowired
	ConferenceService conferenceService;

	@PersistenceContext
	EntityManager entityManager;

	@BeforeClass
	public static void initTestClass() {
		log.debug("===================before class======================");
	}

	@AfterClass
	public static void afterClass() {
		log.debug("===================after class======================");
	}

	@Before
	public void beforeTestCase() {
		log.debug("===================before test=====================");

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

		Long id = conferenceDao.save(newConference());

		entityManager.flush();

		//

		assertTrue(id != null);
		log.debug("id @=" + id);
		Conference conference = conferenceDao.findById(id);

		assertTrue(conference != null);

		assertTrue("JUD2013".equals(conference.getName()));

		// query by slug
		conference = (Conference) conferenceService.findConferenceBySlug("jud-2013");
		
		log.debug("conference @"+conference);

		assertTrue(conference != null);

	}


}

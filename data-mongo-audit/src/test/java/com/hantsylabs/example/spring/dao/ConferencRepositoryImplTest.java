package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hantsylabs.example.spring.config.MongoConfig;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.mongo.ConferenceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={MongoConfig.class})
public class ConferencRepositoryImplTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConferencRepositoryImplTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;


	@Autowired
	MongoTemplate mongoTemplate;

	private Conference newConference() {
		Conference conf = new Conference();
		conf.setName("JUD2013");
		conf.setDescription("JBoss User Developer Conference 2013 Boston");

		log.debug("new conference object:" + conf);
		return conf;
	}


	@BeforeClass
	public static void init() {
		log.debug("==================before class=========================");

	}

	@Before
	public void beforeTestCase() {
		log.debug("==================before test case=========================");
		conferenceRepository.save(newConference());
	}

	@After
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		conferenceRepository.deleteAll();
	}

	@Test
	public void retrieveConference() {
		Conference conference = newConference();
		conference = conferenceRepository.save(conference);

		assertTrue(null != conference.getId());

		conference = conferenceRepository.findByName("JUD2013");
		assertTrue(null != conference);
		assertTrue("hantsy".equals(conference.getCreatedBy()));
		assertTrue(conference.getCreatedDate()!=null);
		log.debug("conference.getLastModifiedBy()@"+conference.getLastModifiedBy());
		log.debug("conference.getLastmodifiedDate()@"+conference.getLastmodifiedDate());
		assertTrue(conference.getLastModifiedBy()==null);
		assertTrue(conference.getLastmodifiedDate()==null);
		
		conference.setName("test");
		conference=conferenceRepository.save(conference);
		assertTrue("test".equals(conference.getName()));
		assertTrue(conference.getLastModifiedBy()!=null);
		assertTrue(conference.getLastmodifiedDate()!=null);
		
	}

	

}

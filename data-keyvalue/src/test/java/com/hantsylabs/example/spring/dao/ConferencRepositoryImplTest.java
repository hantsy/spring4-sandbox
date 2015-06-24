package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.hantsylabs.example.spring.config.DataConfig;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.QConference;
import com.hantsylabs.example.spring.repository.ConferenceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataConfig.class })
public class ConferencRepositoryImplTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConferencRepositoryImplTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;

	private Conference newConference() {
		Conference conf = new Conference();
		conf.setName("JUD2013");
		conf.setSlug("jud-2013");
		conf.setDescription("JBoss User Developer Conference 2013 Boston");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 30);

		log.debug("new conference object:" + conf);
		return conf;
	}

	@BeforeClass
	public static void init() {
		log.debug("==================before class=========================");

	}

	@Before
	@Transactional
	public void beforeTestCase() {
		log.debug("==================before test case=========================");
		conferenceRepository.save(newConference());
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		conferenceRepository.deleteAll();
	}

	@Test
	public void getConference() {
		log.debug("==================test case: getConference=========================");

		List<Conference> saved = (List<Conference>) conferenceRepository.findAll();
		assertTrue(!saved.isEmpty());
	}
	
	@Test
	public void getConferenceQueryDSL() {
		log.debug("==================test case: getConference=========================");

		QConference conf=QConference.conference;
		
		assertTrue(!((List<Conference>)conferenceRepository.findAll(conf.createdDate.before(new Date()))).isEmpty());
	}

}

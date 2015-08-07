package com.hantsylabs.example.spring.dao;

import java.util.Calendar;
import java.util.Date;

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

import com.hantsylabs.example.spring.config.ElasticSearchConfig;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.search.ConferenceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ElasticSearchConfig.class})
public class ConferencRepositoryImplTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConferencRepositoryImplTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;
	
	
//	private Address newAddress() {
//		Address address = new Address();
//		address.setAddressLine1("address line 1");
//		address.setAddressLine2("address line 2");
//		address.setCity("NY");
//		address.setCountry("CN");
//		address.setZipCode("510000");
//
//		return address;
//	}

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

	@BeforeClass
	public static void init() {
		log.debug("==================before class=========================");
		
	}
	
	
	@Before
	@Transactional
	public void beforeTestCase(){
		log.debug("==================before test case=========================");
		conferenceRepository.save(newConference());
	}
	
	
	@After
	@Transactional
	public void afterTestCase(){
		log.debug("==================after test case=========================");
		conferenceRepository.deleteAll();
	}
	
	
	@Test 
	@Transactional
	public void retrieveConference(){
		Conference conference=newConference();
		conference.setSlug("test-jud");
		conference.setName("Test JUD");

			
	} 

}

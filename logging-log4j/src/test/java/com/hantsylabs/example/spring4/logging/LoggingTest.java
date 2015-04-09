package com.hantsylabs.example.spring4.logging;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.hantsylabs.example.spring.config.AppConfig;
import com.hantsylabs.example.spring.config.MongoConfig;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.Signup;
import com.hantsylabs.example.spring.repository.ConferenceRepository;
import com.hantsylabs.example.spring.repository.SignupRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, MongoConfig.class })
@TestExecutionListeners({ TransactionalTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class })
public class LoggingTest {

	private static final Logger log = LoggerFactory
			.getLogger(LoggingTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;

	@Autowired
	private SignupRepository signupRepository;
	
	@Autowired 
	private MongoTemplate mongoTemplate;

	private Conference newConference() {
		Conference conf = new Conference();
		conf.setName("JUD2013");
		conf.setDescription("JBoss User Developer Conference 2013 Boston");

		log.debug("new conference object:" + conf);
		return conf;
	}

	private Signup newSignup() {
		Signup signup = new Signup();

		signup.setEmail("test@test.com");
		signup.setFirstName("Hantsy");
		signup.setLastName("Bai");
		signup.setPhone("123 222 444");

		return signup;
	}

	@BeforeClass
	public static void init() {
		log.debug("==================before class=========================");

	}

	@After
	public void afterTestCase() {
		log.debug("==================after test case=========================");

	}

	Long id;

	@Before
	public void beforeTestCase() {
		log.debug("==================before test case=========================");
		signupRepository.deleteAll();
		conferenceRepository.deleteAll();
	}

	@Test
	public void testConference() {

		Conference conference = newConference();

		conference.setName("Test JUD");

		conference = conferenceRepository.save(conference);
		
		assertTrue(conference.getId()!=null);

		Signup signup1 = newSignup();
		Signup signup2 = newSignup();

		signup1.setEmail("testanother@tom.com");
		signup1.setConference(conference);
		signup2.setEmail("testanother@tom.com");
		signup2.setConference(conference);

		signup1 = signupRepository.save(signup1);
		signup2 = signupRepository.save(signup2);

		List<Signup> signups=mongoTemplate.find(Query.query(Criteria.where("conference").is(conference)), Signup.class);
		
		assertTrue(signups.size()==2);
		
	}

}

package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.Signup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/com/hantsylabs/example/spring/config/applicationContext-jpa.xml")
@TransactionConfiguration
public class RepositoryGenericTypeBeanTest {

	private static final Logger log = LoggerFactory
			.getLogger(RepositoryGenericTypeBeanTest.class);
	
	@Autowired
	JpaRepository<Conference, Long> conferenceRepository;
	
	@Autowired
	JpaRepository<Signup, Long> signupRepository;
	
	
	@BeforeClass
	public static void initTestClass() {
		log.debug("===================before class======================");

	}
	
	@AfterClass
	public static void afterClass() {
		log.debug("===================before class======================");

	}

	@Before
	@org.springframework.transaction.annotation.Transactional
	public void beforeTestCase() {
		log.debug("===================before test=====================");
		
		Conference conf=new Conference("Spring One 2014", new Date());
		Signup user1=new Signup("Hantsy", "Bai", "hantsy@tom.com");	
		Signup user2=new Signup("Tom", "tom", "hantsy@tom.com");
		
		user1.setConference(conf);
		user2.setConference(conf);
		
		conf.getSignups().add(user1);
		conf.getSignups().add(user2);
		
		conferenceRepository.save(conf);
		
	}

	@After
	@org.springframework.transaction.annotation.Transactional
	public void afterTestCase() {
		log.debug("===================after test=====================");
		signupRepository.deleteAll();
		conferenceRepository.deleteAll();
	}

	@Test
	public void testGenericTypeInjection() {
		assertTrue(conferenceRepository!=null);
		assertTrue(signupRepository!=null);
		
		assertTrue(conferenceRepository.findAll().size()==1);
		assertTrue(signupRepository.findAll().size()==2);
	}
	

}

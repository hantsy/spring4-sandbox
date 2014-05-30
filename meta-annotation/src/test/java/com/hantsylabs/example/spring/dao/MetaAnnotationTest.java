package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.annotation.SignupRepository;
import com.hantsylabs.example.spring.config.AppConfig;
import com.hantsylabs.example.spring.config.JpaConfig;
import com.hantsylabs.example.spring.model.Signup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, JpaConfig.class })
@TransactionConfiguration
@Transactional
public class MetaAnnotationTest {

	private static final Logger log = LoggerFactory
			.getLogger(MetaAnnotationTest.class);

	@Autowired
	ApplicationContext ctx;

	@Autowired
	SignupRepository signupRepository;

	@BeforeClass
	public static void initTestClass() {
		log.debug("===================before class======================");
	}

	@Before
	public void beforeTestCase() {
		log.debug("===================before test=====================");
	}

	@After
	public void afterTestCase() {
		log.debug("===================after test=====================");
	}

	@Test
	public void testSignup() {
		log.debug("call testSignup CURD@@@");

		Signup entity = new Signup("Hantsy", "Bai", "hantsy@gmail.com");

		Long savedId = signupRepository.save(entity);
		log.debug("saved id@" + savedId);
		assertTrue(savedId != null);
		
		Signup signup=signupRepository.findById(savedId);
		assertTrue(signup.getFirstName().equals("Hantsy"));

	}

}

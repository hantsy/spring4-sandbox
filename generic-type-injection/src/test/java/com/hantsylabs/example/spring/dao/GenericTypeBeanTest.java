	package com.hantsylabs.example.spring.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
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

import com.hantsylabs.example.spring.config.AppConfig;
import com.hantsylabs.example.spring.config.JpaConfig;
import com.hantsylabs.example.spring.model.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfig.class, AppConfig.class })
public class GenericTypeBeanTest {

	private static final Logger log = LoggerFactory
			.getLogger(GenericTypeBeanTest.class);
	

//	@Autowired
//	List<Conference> availableConferences;
	
	
	@Autowired
	ApplicationContext ctx;
	
/*	@Autowired
	ConferenceService svc;*/
	
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

	
//	@Test
//	public void testConfs() {
//		log.debug("call testConfs@@@");
//		Assert.assertTrue(availableConferences!=null);
//		Assert.assertTrue(availableConferences.size()==3);
//	}
	
	@Test
	public void testConfs2() {
		log.debug("call testConfs2@@@");
		List<Conference> confs2=(List<Conference>)ctx.getBean("availableConferences");
		Assert.assertTrue(confs2!=null);
		Assert.assertTrue(confs2.size()==2);
	}
	
/*	
	@Test
	public void testConfs3() {
		log.debug("call testConfs 3 from ConferenceService@@@");
		List<Conference> confs=svc.availableConferences();
		Assert.assertTrue(confs!=null);
		Assert.assertTrue(confs.size()==3);
	}*/

}

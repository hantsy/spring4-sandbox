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
import com.hantsylabs.example.spring.model.Post;
import com.hantsylabs.example.spring.search.PostRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ElasticSearchConfig.class})
public class PostRepositoryTest {
	private static final Logger log = LoggerFactory
			.getLogger(PostRepositoryTest.class);

	@Autowired
	private PostRepository conferenceRepository;

	private Post newConference() {
		Post conf = new Post();
		conf.setTitle("JUD2013");
		conf.setSlug("jud-2013");
		conf.setContent("JBoss User Developer Conference 2013 Boston");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 30);

		Date startedDate = cal.getTime();

		conf.setCreatedAt(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

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
		Post conference=newConference()

			
	} 

}

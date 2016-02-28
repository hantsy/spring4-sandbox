package com.hantsylabs.example.spring.dao;

import java.util.Calendar;
import java.util.Date;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.config.KeyValueConfig;
import com.hantsylabs.example.spring.model.Post;
import com.hantsylabs.example.spring.model.QPost;
import com.hantsylabs.example.spring.repository.PostRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { KeyValueConfig.class })
public class PostRepositoryTest {
	private static final Logger log = LoggerFactory
			.getLogger(PostRepositoryTest.class);

	@Autowired
	private PostRepository posts;

	private Post newConference() {
		Post conf = new Post();
		//java.security.NoSuchAlgorithmException: NativePRNGBlocking SecureRandom not available
		conf.setId(1L);
		conf.setTitle("JUD2013");
		conf.setSlug("jud-2013");
		conf.setContent("JBoss User Developer Conference 2013 Boston");

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
		posts.save(newConference());
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		posts.deleteAll();
	}

	@Test
	public void getConference() {
		log.debug("==================test case: getConference=========================");

		List<Post> saved = (List<Post>) posts.findAll();
		Assert.assertTrue(!saved.isEmpty());
	}
	
	@Test
	public void getConferenceQueryDSL() {
		log.debug("==================test case: getConference=========================");

		QPost conf=QPost.post;
		
		Assert.assertTrue(!((List<Post>)posts.findAll(conf.createdAt.before(new Date()))).isEmpty());
	}

}

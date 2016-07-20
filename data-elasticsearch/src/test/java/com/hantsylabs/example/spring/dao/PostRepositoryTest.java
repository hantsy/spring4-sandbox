package com.hantsylabs.example.spring.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.hantsylabs.example.spring.config.ElasticSearchConfig;
import com.hantsylabs.example.spring.model.Post;
import com.hantsylabs.example.spring.search.PostRepository;

public class PostRepositoryTest {

	private static final Logger LOG = LoggerFactory.getLogger(PostRepositoryTest.class);

	private PostRepository posts;

	private ElasticsearchOperations elasticsearchOperations;

	AnnotationConfigApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new AnnotationConfigApplicationContext(ElasticSearchConfig.class);

		elasticsearchOperations = ctx.getBean(ElasticsearchOperations.class);
		posts = ctx.getBean(PostRepository.class);

	}

	@After
	public void tearDown() throws Exception {
		ctx.close();
	}

	@Test
	public void newPost() {
		Post conf = new Post();
		conf.setId(1L);
		conf.setTitle("JUD2013");
		conf.setContent("JBoss User Developer Conference 2013 Boston");

		Post saved = posts.save(conf);
		LOG.debug("saved post @" + saved);
		
		Assert.assertEquals(saved.getTitle(), "JUD2013");
		Assert.assertTrue(saved.getContent().contains("JBoss"));
	}

}

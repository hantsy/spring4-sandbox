package com.hantsylabs.example.spring;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hantsylabs.example.spring.es.ESPost;
import com.hantsylabs.example.spring.es.ESPostRepository;
import com.hantsylabs.example.spring.es.ElasticSearchConfig;
import com.hantsylabs.example.spring.es.Receiver;
import com.hantsylabs.example.spring.mongo.MongoConfig;
import com.hantsylabs.example.spring.mongo.Post;
import com.hantsylabs.example.spring.mongo.PostRepository;
import com.hantsylabs.example.spring.mongo.Publisher;

//@RunWith(SpringJUnit4ClassRunner.class)
public class EventTest {

	private static final Logger LOG = LoggerFactory.getLogger(EventTest.class);

	private ESPostRepository esposts;

	private PostRepository posts;

	private Publisher publisher;

	private Receiver receiver;

	AnnotationConfigApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new AnnotationConfigApplicationContext(MongoConfig.class, ElasticSearchConfig.class);

		esposts = ctx.getBean(ESPostRepository.class);
		posts = ctx.getBean(PostRepository.class);
		publisher = ctx.getBean(Publisher.class);
		receiver = ctx.getBean(Receiver.class);

	}

	@After
	public void tearDown() throws Exception {
		ctx.close();
	}

	@Test
	public void testSave() {

		Assert.assertNotNull(esposts);
		Assert.assertNotNull(posts);
		Assert.assertNotNull(publisher);
		Assert.assertNotNull(receiver);

		Post post = new Post();
		post.setTitle("test");
		post.setContent("test content");

		publisher.savePost(post);
		Assert.assertTrue(posts.findAll().size() == 1);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		esposts.findAll().forEach((item) -> {
			LOG.debug("saved post item in es@" + item);
		});

		ESPost postIdx = esposts.findOne("1");
		LOG.debug("post index in es@" + postIdx);

		Assert.assertTrue("test".equals(postIdx.getTitle()));
		Assert.assertTrue("test content".equals(postIdx.getContent()));

	}

}

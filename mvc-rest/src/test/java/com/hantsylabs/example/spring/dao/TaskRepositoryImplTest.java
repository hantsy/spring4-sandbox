package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.config.JpaConfig;
import com.hantsylabs.example.spring.config.WebConfig;
import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfig.class, WebConfig.class })
@WebAppConfiguration
public class TaskRepositoryImplTest {
	private static final Logger log = LoggerFactory
			.getLogger(TaskRepositoryImplTest.class);

	@Autowired
	private TaskRepository taskRepository;

	@PersistenceContext
	EntityManager em;

	private Task newTask() {
		Task conf = new Task();
		conf.setName("Task1");
		conf.setDescription("First task");

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
		taskRepository.save(newTask());
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		taskRepository.deleteAll();
	}

	@Test
	@Transactional
	public void retrieveTask() {
		Task conference = newTask();
		conference.setName("Task1test");
		conference = taskRepository.save(conference);
		em.flush();
		assertTrue(null != conference.getId());
		em.clear();

		conference = taskRepository.findByName("Task1test");
		assertTrue(null != conference);
		em.clear();
	}

}

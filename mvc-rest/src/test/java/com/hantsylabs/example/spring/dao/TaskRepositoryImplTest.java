package com.hantsylabs.example.spring.dao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.inject.Inject;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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

	private MockMvc mvc;
	
	@Inject
	private WebApplicationContext ctx;

	@Autowired
	private TaskRepository taskRepository;

	@PersistenceContext
	EntityManager em;

	private Task newTask() {
		Task conf = new Task();
		conf.setName("test");
		conf.setDescription("Your first test task");
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
		mvc = webAppContextSetup(ctx).build();
		
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		taskRepository.deleteAll();
	}

	@Test
	public void retrieveTasks() throws Exception {

		mvc.perform(get("/api/tasks")).andExpect(status().isOk());

	}
	
	@Test
	public void retrieveSingleTask() throws Exception {
		mvc.perform(
				get("/api/tasks/{id}", 1L)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("id").value(1));

	}

}

package com.hantsylabs.example.spring.test.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hantsylabs.example.spring.model.Task;
import com.hantsylabs.example.spring.test.Fixtures;
import com.hantsylabs.example.spring.web.TaskForm;

@RunWith(BlockJUnit4ClassRunner.class)
public class IntegrationTest {
	private static final Logger log = LoggerFactory.getLogger(IntegrationTest.class);

	private static final String BASE_URL = "http://localhost:8080/mvc-rest/";

	private RestTemplate template;

	@BeforeClass
	public static void init() {
		log.debug("==================before class=========================");
	}

	@Before
	public void beforeTestCase() {
		log.debug("==================before test case=========================");
		template = new RestTemplate();
	}

	@After
	public void afterTestCase() {
		log.debug("==================after test case=========================");
	}

	@Test
	public void testTaskCrudOperations() throws Exception {
		TaskForm newTask = Fixtures.createForm();
		String tasksUrl = BASE_URL + "api/tasks";

		ResponseEntity<Void> postResult = template.postForEntity(tasksUrl, newTask, Void.class);
		assertTrue(HttpStatus.CREATED.equals(postResult.getStatusCode()));
		String createdTaskUrl = postResult.getHeaders().getLocation().toString();
		assertNotNull("created task url should be set", createdTaskUrl);

		ResponseEntity<Task> getTaskResult = template.getForEntity(createdTaskUrl, Task.class);
		assertTrue(HttpStatus.OK.equals(getTaskResult.getStatusCode()));
		log.debug("task @" + getTaskResult.getBody());
		assertTrue(getTaskResult.getBody().getName().equals(newTask.getName()));

		ResponseEntity<Task[]> allTasks = template.getForEntity(tasksUrl, Task[].class);
		assertTrue(HttpStatus.OK.equals(allTasks.getStatusCode()));
		log.debug("task @" + allTasks.getBody());
		assertTrue(allTasks.getBody()[0].getName().equals(newTask.getName()));

		ResponseEntity<Void> deleteResult = template.exchange(createdTaskUrl, HttpMethod.DELETE, null, Void.class);
		assertTrue(HttpStatus.NO_CONTENT.equals(deleteResult.getStatusCode()));

	}

	@Test
	public void noneExistingTask() throws Exception {
		String noneExistingTaskUrl = BASE_URL + "api/tasks/1000";
		try {
			template.getForEntity(noneExistingTaskUrl, Task.class);
		} catch (HttpClientErrorException e) {
			assertTrue(HttpStatus.NOT_FOUND.equals(e.getStatusCode()));
		}
	}

}

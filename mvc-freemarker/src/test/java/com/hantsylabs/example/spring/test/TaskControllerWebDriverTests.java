package com.hantsylabs.example.spring.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TaskControllerWebDriverTests {
	
	@Autowired
	WebApplicationContext context;

	WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		driver = MockMvcHtmlUnitDriverBuilder
				.webAppContextSetup(context)
				.build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateTasks() {
		CreateTaskPage createTask=CreateTaskPage.to(driver);
		TaskListPage taskList = createTask.newTask( "first task", "description of first task");
	}

}

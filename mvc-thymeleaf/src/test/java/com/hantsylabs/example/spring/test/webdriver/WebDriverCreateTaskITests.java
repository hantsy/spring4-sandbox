package com.hantsylabs.example.spring.test.webdriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.hantsylabs.example.spring.model.Task;
import com.hantsylabs.example.spring.test.Assertions;
import com.hantsylabs.example.spring.test.MockDataConfig;
import com.hantsylabs.example.spring.test.webdriver.pages.CreateTaskPage;
import com.hantsylabs.example.spring.test.webdriver.pages.TaskListPage;

@RunWith(BlockJUnit4ClassRunner.class)
public class WebDriverCreateTaskITests {

	WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
	}

	@After
	public void tearDown() throws Exception {
		if (driver != null) {
			driver.close();
		}
	}

	@Test
	public void testCreateTasks() {
		CreateTaskPage createTask = CreateTaskPage.to(driver);
		TaskListPage taskList = createTask.newTask("first task", "description of first task");

		Assertions.assertThat(taskList.getErrors()).isEqualTo("Task is created sucessfully!");
	}

	@Test
	public void testCreateTaskWithEmptyFields() {
		CreateTaskPage createTask = CreateTaskPage.to(driver);
		CreateTaskPage createTaskPage = createTask.newTaskWithEmptyFields();

		Assertions.assertThat(createTaskPage.getErrors()).isEqualTo("Invalid input data!");
	}

}

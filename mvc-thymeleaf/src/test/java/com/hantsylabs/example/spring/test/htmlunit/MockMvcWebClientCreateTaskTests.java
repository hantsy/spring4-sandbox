package com.hantsylabs.example.spring.test.htmlunit;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.hantsylabs.example.spring.config.AppConfig;
import com.hantsylabs.example.spring.config.WebConfig;
import com.hantsylabs.example.spring.test.Assertions;
import com.hantsylabs.example.spring.test.MockDataConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, MockDataConfig.class, WebConfig.class })
@WebAppConfiguration
//@WithMockUser
public class MockMvcWebClientCreateTaskTests {

	@Autowired
	WebApplicationContext context;

	WebClient webClient;

	@Before
	public void setUp() throws Exception {
		webClient = MockMvcWebClientBuilder
				.webAppContextSetup(context)
				.withDelegate(new WebClient(BrowserVersion.CHROME))
				//.webAppContextSetup(context, springSecurity())
				// for illustration only - defaults to ""
				.contextPath("")
				//.useMockMvcForHosts("example.com","example.org")
				.build();
	}

	@After
	public void tearDown() throws Exception {
		this.webClient.close();
	}

	@Test
	public void testCreateTasks() throws Exception {

		HtmlPage createTaskPage = webClient.getPage("http://localhost:8080/tasks/new");

		HtmlForm form = createTaskPage.getHtmlElementById("form");
		HtmlTextInput nameInput = createTaskPage.getHtmlElementById("name");
		nameInput.setValueAttribute("My first task");
		HtmlTextArea descriptionInput = createTaskPage.getHtmlElementById("description");
		descriptionInput.setText("Description of my first task");
		HtmlButton submit = form.getOneHtmlElementByAttribute("button", "type", "submit");
		HtmlPage taskListPage = submit.click();

		Assertions.assertThat(taskListPage.getUrl().toString()).endsWith("/tasks");
//		String id = taskListPage.getHtmlElementById("todolist").getTextContent();
//		assertThat(id).isEqualTo("123");
//		String summary = newMessagePage.getHtmlElementById("summary").getTextContent();
//		assertThat(summary).isEqualTo("Spring Rocks");
//		String text = newMessagePage.getHtmlElementById("text").getTextContent();
//		assertThat(text).isEqualTo("In case you didn't know, Spring Rocks!");

	}

}

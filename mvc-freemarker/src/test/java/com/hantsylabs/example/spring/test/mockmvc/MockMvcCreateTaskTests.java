/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.hantsylabs.example.spring.test.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.hantsylabs.example.spring.config.WebConfig;
import com.hantsylabs.example.spring.test.MockDataConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, MockDataConfig.class})
@WebAppConfiguration
public class MockMvcCreateTaskTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void createTask() throws Exception {
		mockMvc.perform(post("/tasks")
				.param("name", "First task")
				.param("description", "Description of my first task"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/tasks"));
	}

	@Test
	public void createTaskForm() throws Exception {
		mockMvc.perform(get("/tasks/new"))
			.andExpect(xpath("//input[@name='name']").exists())
			.andExpect(xpath("//textarea[@name='description']").exists());
	}

	@Test
	public void createTaskFormSubmit() throws Exception {

		String nameParamName = "name";
		String descriptionParamName = "description";
		mockMvc.perform(get("/tasks/new"))
				.andExpect(xpath("//input[@name='" + nameParamName + "']").exists())
				.andExpect(xpath("//textarea[@name='" + descriptionParamName + "']").exists());

		MockHttpServletRequestBuilder createTask = post("/tasks")
				.param(nameParamName, "First task")
				.param(descriptionParamName, "Description of my first task");

		mockMvc.perform(createTask)
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/tasks"));
		
		mockMvc.perform(get("/tasks"))
	   		   .andExpect(xpath("//ul[@id='todotasks']//li[@class='list-group-item']").exists());
		mockMvc.perform(get("/tasks"))
		   .andExpect(xpath("//ul[@id='doingtasks']//li[@class='list-group-item']").exists());
		mockMvc.perform(get("/tasks"))
		   .andExpect(xpath("//ul[@id='donetasks']//li[@class='list-group-item']").exists());
		
	}
}

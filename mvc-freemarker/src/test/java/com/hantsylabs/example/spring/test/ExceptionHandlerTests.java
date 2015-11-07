package com.hantsylabs.example.spring.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import javax.validation.constraints.NotNull;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hantsylabs.example.spring.web.TaskController;
import com.hantsylabs.example.spring.web.TaskNotFoundException;

public class ExceptionHandlerTests {

	@Test
	public void testExceptionHandlerMethod() throws Exception {
		standaloneSetup(new TestTaskController()).build().perform(get("/tasks/1000")).andExpect(status().isOk())
				.andExpect(forwardedUrl("error"));
	}

	@Controller
	@RequestMapping(value = "/tasks")
	private static class TestTaskController extends TaskController {

		@RequestMapping(value = "{id}", method = RequestMethod.GET)
		public String getTask(@PathVariable("id") @NotNull Long id, Model model) {
			if (id == 1000) {
				throw new TaskNotFoundException(id);
			}
			return super.getTask(id, model);
		}

	}

}

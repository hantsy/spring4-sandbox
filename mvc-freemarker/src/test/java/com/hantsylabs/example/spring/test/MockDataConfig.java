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
package com.hantsylabs.example.spring.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;
import com.hantsylabs.example.spring.web.TaskNotFoundException;

@Configuration
public class MockDataConfig {

	@Bean
	public TaskRepository taskRepository() {
		final Task task = Fixtures.createTask();
		final Task doingTask=Fixtures.createDoingTask();
		final Task doneTask = Fixtures.createDoneTask();
		
		TaskRepository tasks = mock(TaskRepository.class);
		when(tasks.save(any(Task.class))).thenAnswer(new Answer<Task>() {
			@Override
			public Task answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Task result = (Task) args[0];
				result.setId(task.getId());
				result.setCreatedDate(task.getCreatedDate());
				return result;
			}
		});
		when(tasks.findOne(1000L)).thenThrow(new TaskNotFoundException(1000L));
		
		when(tasks.findOne(1L)).thenReturn(task);
		when(tasks.findOne(2L)).thenReturn(doingTask);
		when(tasks.findOne(3L)).thenReturn(doneTask);
		
		when(tasks.findByStatus(Status.TODO, new Sort(Direction.DESC, "lastModifiedDate"))).thenReturn(Arrays.asList(task));
		when(tasks.findByStatus(Status.DOING, new Sort(Direction.DESC, "lastModifiedDate"))).thenReturn(Arrays.asList(doingTask));
		when(tasks.findByStatus(Status.DONE, new Sort(Direction.DESC, "lastModifiedDate"))).thenReturn(Arrays.asList(doneTask));
		
		when(tasks.findAll()).thenReturn(Arrays.asList(task, doingTask, doneTask));
		return tasks;
	}

	@Bean
	public Task createTask() {
		return Fixtures.createTask();
	}
}

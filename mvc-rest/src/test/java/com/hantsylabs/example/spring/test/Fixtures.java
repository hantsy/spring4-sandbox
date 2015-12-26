package com.hantsylabs.example.spring.test;

import com.hantsylabs.example.spring.model.Task;
import com.hantsylabs.example.spring.web.TaskForm;

public class Fixtures {
	
	public static Task create() {
		Task task = new Task();
		task.setName("test");
		task.setDescription("Your first test task");
		return task;
	}

	public static TaskForm createForm() {
		TaskForm task = new TaskForm();
		task.setName("test");
		task.setDescription("Your first test task");
		return task;
	}
}

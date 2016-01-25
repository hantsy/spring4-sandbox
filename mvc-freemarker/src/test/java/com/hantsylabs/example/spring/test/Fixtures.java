package com.hantsylabs.example.spring.test;

import java.util.Date;

import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;

public class Fixtures {
	
	public static Task createTask() {
		Task task = new Task();
		task.setCreatedDate(new Date());
		task.setId(1L);
		task.setName("First task");
		task.setDescription("Description of my first task!");
		return task;
	}
	
	public static Task createDoingTask(){
		Task task = new Task();
		task.setCreatedDate(new Date());
		task.setId(2L);
		task.setName("Doing task");
		task.setStatus(Status.DOING);
		task.setDescription("Description of my doing task!");
		return task;
	}
	
	public static Task createDoneTask(){
		Task task = new Task();
		task.setCreatedDate(new Date());
		task.setId(3L);
		task.setName("Done task");
		task.setStatus(Status.DONE);
		task.setDescription("Description of my done task!");
		return task;
	}
}

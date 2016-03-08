package com.hantsylabs.example.spring.test;

import com.hantsylabs.example.spring.model.Task;

public class Assertions extends org.fest.assertions.api.Assertions {
	
	public static TaskAssert assertThat(Task task){
		return new TaskAssert(task);
	}

}
	
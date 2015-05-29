package com.hantsylabs.example.spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

	public TaskNotFoundException(Long taskId) {
		super(String.format("task:{0} not found!", taskId));
	}

}

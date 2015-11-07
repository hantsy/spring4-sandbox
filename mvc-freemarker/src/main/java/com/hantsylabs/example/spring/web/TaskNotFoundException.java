package com.hantsylabs.example.spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TaskNotFoundException extends RuntimeException {

	public TaskNotFoundException(Long taskId) {
		super(String.format("task:%s not found!", taskId));
	}

}

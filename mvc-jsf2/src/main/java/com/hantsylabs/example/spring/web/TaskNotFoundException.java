package com.hantsylabs.example.spring.web;

public class TaskNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(Long taskId) {
		super(String.format("task:{0} not found!", taskId));
	}

}

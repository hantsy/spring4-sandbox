package com.hantsylabs.example.spring.web;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Task;

/**
 * 
 * @author hantsy
 *
 */
@Named("viewTaskAction")
@Scope(value = "view")
public class ViewTaskDetailsAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ViewTaskDetailsAction.class);

	@Inject
	private TaskRepository taskRepository;

	@NotNull
	private Long taskId;

	private Task task;

	public void init() {

		log.debug(" get task of id @" + taskId);

		task = taskRepository.findOne(taskId);

		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}

	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

}

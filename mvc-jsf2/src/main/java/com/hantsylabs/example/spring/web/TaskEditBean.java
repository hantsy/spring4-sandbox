package com.hantsylabs.example.spring.web;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;

/**
 * 
 * @author hantsy
 *
 */
@Named()
@Scope(value = "session")
public class TaskEditBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(TaskEditBean.class);

	@Inject
	private TaskRepository taskRepository;

	private Long taskId;

	private Task task;	

	public void initTask() {

		log.debug(" get task of id @" + taskId);
		if (taskId != null) {
			task = new Task();
		}

		task = taskRepository.findOne(taskId);

		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}

	}

	public void saveTask() {
		taskRepository.save(task);
	}

	public void markTaskDoing(Long id) {

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setStatus(Status.DOING);

		taskRepository.save(task);
	}

	public void markTaskDone(Long id) {

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setStatus(Status.DONE);

		taskRepository.save(task);

	}

}

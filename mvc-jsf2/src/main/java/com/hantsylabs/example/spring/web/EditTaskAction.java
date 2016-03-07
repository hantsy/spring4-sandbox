package com.hantsylabs.example.spring.web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
@Named("editTaskAction")
@Scope(value = "view")
public class EditTaskAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(EditTaskAction.class);

	@Inject
	private TaskRepository taskRepository;

	private Long taskId;

	private Task task;

	public Task getTask() {
		return task;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void init() {
		log.debug(" get task of id @" + taskId);
		
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (taskId == null) {
				task = new Task();
			} else {
				task = taskRepository.findOne(taskId);
				if (task == null) {
					throw new TaskNotFoundException(taskId);
				}
			}
		}
	}

	public String save() {
		log.debug("saving task@" + task);
		this.task = taskRepository.save(task);
		FacesMessage info= new FacesMessage(FacesMessage.SEVERITY_INFO, "Task is saved successfully!",   "Task is saved successfully!");
		FacesContext.getCurrentInstance().addMessage(null, info);
		
		return "/tasks.xhtml?faces-redirect=true";
	}

}

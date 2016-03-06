package com.hantsylabs.example.spring.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;

/**
 * 
 * @author hantsy
 *
 */
@Named("taskHome")
@Scope(value = "view")
public class TaskHome {
	private static final Logger log = LoggerFactory.getLogger(TaskHome.class);

	@Inject
	private TaskRepository taskRepository;

	private List<TaskDetails> todotasks = new ArrayList<>();

	private List<TaskDetails> doingtasks = new ArrayList<>();

	private List<TaskDetails> donetasks = new ArrayList<>();

	public List<TaskDetails> getTodotasks() {
		return todotasks;
	}

	public List<TaskDetails> getDoingtasks() {
		return doingtasks;
	}

	public List<TaskDetails> getDonetasks() {
		return donetasks;
	}

	public void init() {
		log.debug("initalizing...");
		if (!FacesContext.getCurrentInstance().isPostback()) {
			retrieveAllTasks();
		}
	}

	private void retrieveAllTasks() {
		log.debug("retriveing all tasks...");
		this.todotasks = findTasksByStatus(Status.TODO);
		this.doingtasks = findTasksByStatus(Status.DOING);
		this.donetasks = findTasksByStatus(Status.DONE);
	}

	private List<TaskDetails> findTasksByStatus(Status status) {
		List<TaskDetails> taskList = new ArrayList<TaskDetails>();
		List<Task> tasks = taskRepository.findByStatus(status, new Sort(Direction.DESC, "lastModifiedDate"));

		for (Task task : tasks) {
			TaskDetails details = new TaskDetails();
			details.setId(task.getId());
			details.setName(task.getName());
			details.setDescription(task.getDescription());
			details.setCreatedDate(task.getCreatedDate());
			details.setLastModifiedDate(task.getLastModifiedDate());
			taskList.add(details);
		}

		return taskList;
	}

	public void deleteTask(Long id) {

		log.debug("delete task of id@" + id);

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		taskRepository.delete(id);

		// retrieve all tasks
		retrieveAllTasks();
	}

	public void markTaskDoing(Long id) {
		log.debug("changing task DONG @" + id);

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setStatus(Status.DOING);

		taskRepository.save(task);

		// retrieve all tasks
		retrieveAllTasks();
	}

	public void markTaskDone(Long id) {
		log.debug("changing task DONE @" + id);

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setStatus(Status.DONE);

		taskRepository.save(task);

		// retrieve all tasks
		retrieveAllTasks();

	}

}

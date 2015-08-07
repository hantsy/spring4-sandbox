package com.hantsylabs.example.spring.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;

/**
 * 
 * @author hantsy
 *
 */
@Named
@Scope(value = "request")
public class TaskHome {
	private static final Logger log = LoggerFactory.getLogger(TaskHome.class);

	@Inject
	private TaskRepository taskRepository;

	private List<TaskDetails> taskList = new ArrayList<>();
	
	public List<TaskDetails> getTaskList() {
		return taskList;
	}

	public void retrieveAllTasks() {

		Sort sort = new Sort(Direction.DESC, "lastModifiedDate");
		List<Task> tasks = taskRepository.findAll(sort);

		for (Task task : tasks) {
			TaskDetails details = new TaskDetails();
			details.setName(task.getName());
			details.setDescription(task.getDescription());
			details.setCreatedDate(task.getCreatedDate());
			details.setLastModifiedDate(task.getLastModifiedDate());
			taskList.add(details);
		}

	}

	public void deleteTask(Long id) {

		log.debug("delete task of id@" + id);

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		taskRepository.delete(id);
		
		//retrieve all tasks
		retrieveAllTasks();
	}

}

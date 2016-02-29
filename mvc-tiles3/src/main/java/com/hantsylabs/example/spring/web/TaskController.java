package com.hantsylabs.example.spring.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hantsylabs.example.spring.jpa.TaskRepository;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;

/**
 * 
 * @author hantsy
 *
 */
@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	private TaskRepository taskRepository;

	@Inject
	private TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String allTask(Model model) {

		List<TaskDetails> todolist = findByStatus(Status.TODO);

		model.addAttribute("todotasks", todolist);

		List<TaskDetails> doinglist = findByStatus(Status.DOING);

		model.addAttribute("doingtasks", doinglist);

		List<TaskDetails> donelist = findByStatus(Status.DONE);

		model.addAttribute("donetasks", donelist);

		return "tasks";
	}

	private List<TaskDetails> findByStatus(Status status) {
		Sort sort = new Sort(Direction.DESC, "lastModifiedDate");

		List<TaskDetails> detailsList = new ArrayList<>();
		List<Task> tasks = taskRepository.findByStatus(status, sort);

		for (Task task : tasks) {
			TaskDetails details = new TaskDetails();
			details.setId(task.getId());
			details.setName(task.getName());
			details.setDescription(task.getDescription());
			details.setCreatedDate(task.getCreatedDate());
			details.setLastModifiedDate(task.getLastModifiedDate());
			detailsList.add(details);
		}
		return detailsList;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String showAddTask(ModelMap model) {
		model.put("task", new TaskForm());
		return "add";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String createTask(@ModelAttribute("task") @Valid TaskForm fm, BindingResult result,
			RedirectAttributes redirectAttrs) {
		log.debug("saving task @" + fm);
		if (result.hasErrors()) {
			redirectAttrs.addFlashAttribute("flashMessage", AlertMessage.danger("Invalid input data!"));
			return "add";
		}

		Task task = new Task();
		task.setName(fm.getName());
		task.setDescription(fm.getDescription());

		task = taskRepository.save(task);

		redirectAttrs.addFlashAttribute("flashMessage", AlertMessage.success("Task is created sucessfully!"));

		return "redirect:/tasks";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String editTask(@PathVariable("id") Long id, ModelMap model) {

		log.debug("editing task @" + id);

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		TaskForm fm = new TaskForm();

		fm.setId(task.getId());
		fm.setName(task.getName());
		fm.setDescription(task.getDescription());

		model.addAttribute("task", fm);

		return "edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, params = "!action")
	public String updateTask(@PathVariable("id") Long id, @ModelAttribute("task") @Valid TaskForm fm,
			BindingResult result, RedirectAttributes redirectAttrs) {

		log.debug("updating task @" + fm);
		if (result.hasErrors()) {
			return "edit";
		}

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setName(fm.getName());
		task.setDescription(fm.getDescription());

		taskRepository.save(task);

		redirectAttrs.addFlashAttribute("flashMessage", AlertMessage.info("Task is updated sucessfully!"));

		return "redirect:/tasks";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, params = "action=MARK_DOING")
	public String markTaskDoing(@PathVariable("id") Long id) {
		log.debug("start work @" + id);

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setStatus(Status.DOING);

		taskRepository.save(task);

		return "redirect:/tasks";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, params = "action=MARK_DONE")
	public String markTaskDone(@PathVariable("id") Long id) {

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		task.setStatus(Status.DONE);

		taskRepository.save(task);

		return "redirect:/tasks";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getTask(@PathVariable("id") @NotNull Long id, Model model) {

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		TaskDetails details = new TaskDetails();
		details.setId(task.getId());
		details.setName(task.getName());
		details.setStatus(task.getStatus().name());
		details.setDescription(task.getDescription());
		details.setCreatedDate(task.getCreatedDate());
		details.setLastModifiedDate(task.getLastModifiedDate());

		if (log.isDebugEnabled()) {
			log.debug("task details@" + details);
		}

		model.addAttribute("details", details);

		return "details";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteTask(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {

		Task task = taskRepository.findOne(id);

		if (task == null) {
			throw new TaskNotFoundException(id);
		}

		taskRepository.delete(id);
		
		redirectAttrs.addFlashAttribute("flashMessage", AlertMessage.danger("Task "+id+" is deleted!"));

		return "redirect:/tasks";
	}
	
	@ExceptionHandler(value=TaskNotFoundException.class)
	public String notFound(TaskNotFoundException ex, Model model){
		model.addAttribute("ex", ex.getMessage());	
		return "error";
	}

}

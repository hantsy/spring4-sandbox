package com.hantsylabs.example.spring.jpa;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.Task;



public interface TaskRepository extends 
		JpaRepository<Task, Long> {
	
	Task findByName(String name);
	
	List<Task> findByStatus(Status status, Sort sort);
}

package com.hantsylabs.example.spring.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hantsylabs.example.spring.model.Task;

public interface TaskRepository extends 
		JpaRepository<Task, Long> {
	
	Task findByName(String name);
}

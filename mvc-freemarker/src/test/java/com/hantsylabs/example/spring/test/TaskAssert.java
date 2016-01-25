package com.hantsylabs.example.spring.test;

import org.fest.assertions.api.ObjectAssert;
import static org.fest.assertions.api.Assertions.assertThat;
import com.hantsylabs.example.spring.model.Task;

public class TaskAssert extends ObjectAssert<Task> {

	protected TaskAssert(Task actual) {
		super(actual);
	}


	@Override
	public TaskAssert isEqualTo(Task expected) {
		isEqualToIgnoringGeneratedFields(expected);
		assertThat(expected.getId()).isEqualTo(actual.getId());
		assertThat(expected.getCreatedDate()).isEqualTo(actual.getCreatedDate());
		assertThat(expected.getLastModifiedDate()).isEqualTo(actual.getLastModifiedDate());
		return this;
	}


	public TaskAssert isEqualToIgnoringGeneratedFields(Task expected) {
		assertThat(expected.getName()).isEqualTo(actual.getName());
		assertThat(expected.getDescription()).isEqualTo(actual.getDescription());
		return this;
	}
	
	

}

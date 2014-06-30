package com.hantsylabs.example.spring.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SignupInfo {

	@Id
	private String id;

	@DBRef
	private List<Signup> signups = new ArrayList<Signup>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Signup> getSignups() {
		return signups;
	}

	public void setSignups(List<Signup> signups) {
		this.signups = signups;
	}

}

package com.hantsylabs.example.spring.mongo;

import javax.inject.Named;

import org.springframework.data.domain.AuditorAware;

@Named(value="auditor")
public class Auditor implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		return "hantsy";
	}

}

package com.hantsylabs.example.spring.config;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hantsylabs.example.spring.model.Conference;

@Configuration
public class AppConfig {

	@Bean
	public List<Conference> availableConfs() {
		return Arrays.asList(new Conference("JavaOne", new Date()),
				new Conference("SpringOne", new Date()));
	}

}

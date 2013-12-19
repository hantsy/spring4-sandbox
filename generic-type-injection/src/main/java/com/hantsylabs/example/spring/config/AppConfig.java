package com.hantsylabs.example.spring.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hantsylabs.example.spring.model.Conference;

@Configuration
public class AppConfig {
	private static final Logger log=LoggerFactory.getLogger(AppConfig.class);
	
	@Bean()
	public ConferenceList availableConferences(){
		log.debug("@@@create bean @availableConferences");
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		
		Date nextMonth=cal.getTime();
		return new  ConferenceList(Arrays.asList(new Conference("Spring One 2004", nextMonth),
				new Conference("Java One 2004", nextMonth),
				new Conference("JUDConf 2004", nextMonth)
				));
	}
	
	public static class ConferenceList extends ArrayList<Conference> implements List<Conference>{

		public ConferenceList(List<Conference> asList) {
			super(asList);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}

}

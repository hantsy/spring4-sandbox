package com.hantsylabs.example.spring.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.hantsylabs.example.spring.model.Conference;

public class ConferenceItemProcessor implements ItemProcessor<Conference, Conference> {
	private static final Logger log =LoggerFactory.getLogger(ConferenceItemProcessor.class);

	@Override
	public Conference process(Conference item) throws Exception {
		
		log.debug("convert name to lowercase @"+ item.getName());
		
		item.setName(item.getName().toLowerCase());
		
		log.debug("converted @"+ item.getName());
		return item;
	}

}

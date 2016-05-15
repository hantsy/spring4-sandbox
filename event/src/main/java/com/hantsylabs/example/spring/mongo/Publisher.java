package com.hantsylabs.example.spring.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class Publisher implements ApplicationEventPublisherAware {

	private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

	@Autowired
	PostRepository repository;

	private ApplicationEventPublisher publisher;
	
	public Publisher() {
	}


	public void savePost(Post post) {
		Post saved = repository.save(post);
		this.publisher.publishEvent(saved);

		LOG.debug("saved post data in mongo@" + saved);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

}

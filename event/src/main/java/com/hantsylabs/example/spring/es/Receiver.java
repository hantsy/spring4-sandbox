package com.hantsylabs.example.spring.es;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hantsylabs.example.spring.mongo.Post;

@Component
public class Receiver {
	
	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	ESPostRepository repository;

	@EventListener
	public void onPostSaved(Post savedPost) {
		LOG.debug("=================received post data============== @\r\n"+ savedPost);
		
		ESPost doc=new ESPost();
		doc.setId("1");
		doc.setTitle(savedPost.getTitle());
		doc.setContent(savedPost.getContent());
		repository.save(doc);
	}

}

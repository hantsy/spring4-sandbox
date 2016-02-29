package com.hantsylabs.example.spring.mongo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.hantsylabs.example.spring.model.Conference;
import com.mongodb.WriteResult;


public class ConferenceRepositoryImpl implements ConferenceRepositoryCustom {
	private static final Logger log = LoggerFactory
			.getLogger(ConferenceRepositoryImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Conference> searchByDescription(String d) {
		return mongoTemplate.find(
				Query.query(Criteria.where("description").regex(
						"[\\w]*" + d + "[\\w]*", "i")), Conference.class);
	}

	@Override
	public void updateConferenceDescription(String description, String id) {
		WriteResult result = mongoTemplate.updateMulti(
				Query.query(Criteria.where("id").is(id)),
				Update.update("description", description), Conference.class);
		
		log.debug("result @"+result.getN());
	}

}

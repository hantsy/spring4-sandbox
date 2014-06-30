package com.hantsylabs.example.spring.model;

import org.hibernate.envers.RevisionListener;

public class ConferenceRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		ConferenceRevisionEntity entity=(ConferenceRevisionEntity) revisionEntity;
		entity.setAuditor(SecurityUtils.getCurrentUser());
	}

}

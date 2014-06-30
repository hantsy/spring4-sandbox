package com.hantsylabs.example.spring.model;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditEntityListener {
	@PrePersist
	public void prePersist(Object o) {
		final Date _createdDate = new Date();

		if (o instanceof AuditableEntity) {
			AuditableEntity entity = (AuditableEntity) o;
			entity.setCreatedDate(_createdDate);
			entity.setModifiedDate(_createdDate);
			entity.setCreatedBy(SecurityUtils.getCurrentUser());
			entity.setModifiedBy(SecurityUtils.getCurrentUser());
		}

	}

	@PreUpdate
	public void preUpdate(Object o) {
		if (o instanceof AuditableEntity) {
			AuditableEntity entity = (AuditableEntity) o;
			entity.setModifiedDate(new Date());
			entity.setModifiedBy(SecurityUtils.getCurrentUser());
		}
	}
}

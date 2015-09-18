package com.hantsylabs.example.spring.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;


@Entity
@RevisionEntity(ConferenceRevisionListener.class)
public class ConferenceRevisionEntity extends DefaultRevisionEntity{
	
	@ManyToOne
	@JoinColumn(name="auditor_id")
	private User auditor;

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}	
	
	
}

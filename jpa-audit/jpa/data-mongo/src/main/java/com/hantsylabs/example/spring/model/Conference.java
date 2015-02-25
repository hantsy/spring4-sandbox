package com.hantsylabs.example.spring.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;


@Document
public class Conference {

	@org.springframework.data.annotation.Id
	private String id;

	@org.springframework.data.annotation.Version
	private Integer version;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	@DateTimeFormat(style = "M-")
	private Date startedDate;

	@NotNull
	@DateTimeFormat(style = "M-")
	private Date endedDate;

	@NotNull
	private String slug;
	
	private Address address;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartedDate() {
		return this.startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public Date getEndedDate() {
		return this.endedDate;
	}

	public void setEndedDate(Date endedDate) {
		this.endedDate = endedDate;
	}

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}

package com.hantsylabs.example.spring.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

@Document(indexName="conference_idx")
public class Conference {
	@org.springframework.data.annotation.Id
	private Long id;

	@org.springframework.data.annotation.Version
	private Integer version;

	@NotNull
	@Field(store=true, index=FieldIndex.analyzed)
	private String name;

	@NotNull
	@Field(store=true, index=FieldIndex.analyzed)
	private String description;
	
	@Field(store=false, index=FieldIndex.not_analyzed)
	private Date startedDate;

	@Field(store=false, index=FieldIndex.not_analyzed)
	private Date endedDate;

	@NotNull
	@Field(store=false, index=FieldIndex.not_analyzed)
	private String slug;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Conference [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description
				+ ", startedDate=" + startedDate + ", endedDate=" + endedDate + ", slug=" + slug + "]";
	}



}

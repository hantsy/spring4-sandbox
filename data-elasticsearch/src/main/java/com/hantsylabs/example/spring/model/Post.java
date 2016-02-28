package com.hantsylabs.example.spring.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

@Document(indexName = "conference_idx")
public class Post {
	@org.springframework.data.annotation.Id
	private Long id;

	@org.springframework.data.annotation.Version
	private Integer version;

	@NotNull
	@Field(store = true, index = FieldIndex.analyzed)
	private String title;

	@NotNull
	@Field(store = true, index = FieldIndex.analyzed)
	private String content;

	@Field(store = false, index = FieldIndex.not_analyzed)
	private Date createdAt;

	@NotNull
	@Field(store = false, index = FieldIndex.not_analyzed)
	private String slug;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", version=" + version + ", title=" + title + ", content=" + content + ", createdAt="
				+ createdAt + ", slug=" + slug + "]";
	}

}

package com.hantsylabs.example.spring.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Conference {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@NotNull
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "started_date")
	private Date startedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	@Column(name = "ended_date")
	private Date endedDate;

	@NotNull
	@Column(name = "slug")
	private String slug;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="conference")
	private List<Signup> signups=new ArrayList<Signup>();

	public Conference(String name, Date startedDate) {
		super();
		this.name = name;
		this.startedDate = startedDate;
		this.slug=this.name.trim().replaceAll(" ", "-").toLowerCase();
	}
	
	
	public Conference() {
		super();
	}
	
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Signup> getSignups() {
		return signups;
	}


	public void setSignups(List<Signup> signups) {
		this.signups = signups;
	}


	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}

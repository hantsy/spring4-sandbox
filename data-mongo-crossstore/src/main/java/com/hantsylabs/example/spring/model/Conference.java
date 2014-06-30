package com.hantsylabs.example.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.crossstore.RelatedDocument;
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
	private String name;

	@NotNull
	private String description;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date startedDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date endedDate;

	@NotNull
	private String slug;

	@RelatedDocument
	// @Transient
	private Contact contact;

	@RelatedDocument
	private SignupInfo signupInfo;

	// @RelatedDocument
	// //@Transient
	// private Set<Signup> signups = new HashSet<Signup>();

	public SignupInfo getSignupInfo() {
		return signupInfo;
	}

	public void setSignupInfo(SignupInfo signupInfo) {
		this.signupInfo = signupInfo;
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

	// public Set<Signup> getSignups() {
	// return this.signups;
	// }
	//
	// public void setSignups(Set<Signup> signups) {
	// this.signups = signups;
	// }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	// public void addSignup(Signup signup) {
	// if (!this.signups.contains(signup)) {
	// signups.add(signup);
	// //signup.setConference(this);
	// }
	// }
}

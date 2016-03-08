package com.hantsylabs.example.spring.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Conference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2257263473926908340L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private String slug;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date startedDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date endedDate;

	private Address address;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "conference")
	private Set<Signup> signups = new HashSet<>();

	@DateTimeFormat(style = "M-")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@DateTimeFormat(style = "M-")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@ManyToOne
	private User createdBy;

	@ManyToOne
	private User modifiedBy;

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Set<Signup> getSignups() {
		return signups;
	}

	public void setSignups(Set<Signup> signups) {
		this.signups = signups;
	}

	public void addSignup(Signup newSignup) {
		if (!signups.contains(newSignup)) {
			this.signups.add(newSignup);
			newSignup.setConference(this);
		}
	}

	@PrePersist
	public void prePersist() {
		final Date _createdDate = new Date();

		this.createdDate = _createdDate;
		this.modifiedDate = _createdDate;
		this.createdBy = SecurityUtils.getCurrentUser();
		this.modifiedBy = SecurityUtils.getCurrentUser();
	}

	@PreUpdate
	public void preUpdate() {
		this.modifiedDate = new Date();
		this.modifiedBy = SecurityUtils.getCurrentUser();
	}

}

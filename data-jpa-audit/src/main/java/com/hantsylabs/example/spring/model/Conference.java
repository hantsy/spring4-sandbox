package com.hantsylabs.example.spring.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Conference extends AbstractAuditable<User, Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2257263473926908340L;

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
	private Set<Signup> signups = new HashSet<Signup>();

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
		if(!signups.contains(newSignup)){
			this.signups.add(newSignup);
			newSignup.setConference(this);
		}
	}

}

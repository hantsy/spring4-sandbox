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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Audited
public class Conference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2257263473926908340L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Version
	private Long version;

	@NotNull
	private String name;

	@NotNull
	@Audited(withModifiedFlag=true)
	private String description;

	@NotNull
	@Audited(withModifiedFlag=true)
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


	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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




	@Override
	public String toString() {
		return "Conference [id=" + id + ", version=" + version + ", name="
				+ name + ", description=" + description + ", slug=" + slug
				+ ", startedDate=" + startedDate + ", endedDate=" + endedDate
				+ ", address=" + address + "]";
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

}

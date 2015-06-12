package com.hantsylabs.example.spring.jpa.spec;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.hantsylabs.example.spring.model.Conference;

public class JpaSpecs {

	public static Specification<Conference> inProgressConferences() {
		return new Specification<Conference>() {

			@Override
			public Predicate toPredicate(Root<Conference> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Expression<Timestamp> currentTimestamp = cb.currentTimestamp();
				return cb.and(
						cb.greaterThan(root.get("endedDate").as(Date.class),
								currentTimestamp), cb.lessThan(
								root.get("startedDate").as(Date.class),
								currentTimestamp));
			}

		};
	}

	public static Specification<Conference> pastConferences(final Date past) {
		return new Specification<Conference>() {
			@Override
			public Predicate toPredicate(Root<Conference> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Expression<Timestamp> currentTimestamp = cb.currentTimestamp();
				if (past == null) {
					return cb.greaterThan(currentTimestamp,
							root.get("endedDate").as(Date.class));
				} else {
					return cb.and(cb.greaterThan(currentTimestamp,
							root.get("endedDate").as(Date.class)), cb
							.greaterThan(
									root.get("startedDate").as(Date.class),
									past));
				}
			}
		};
	}

	public static Specification<Conference> upcomingConferences() {
		return new Specification<Conference>() {
			@Override
			public Predicate toPredicate(Root<Conference> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.greaterThan(root.get("startedDate").as(Date.class),
						cb.currentTimestamp());
			}
		};
	}
}

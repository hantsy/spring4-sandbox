package com.hantsylabs.example.spring.jpa.spec;

import java.util.Date;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class QueryDslPredicates {

	public static Predicate inProgressConferences() {
		QConference conf = QConference.conference;
		final Date now = new Date();
		BooleanBuilder builder = new BooleanBuilder();
		return builder.and(conf.startedDate.before(now))
				.and(conf.endedDate.after(now)).getValue();

	}

	public static Predicate pastConferences(Date _past) {
		QConference conf = QConference.conference;

		final Date now = new Date();
		BooleanBuilder builder = new BooleanBuilder();

		builder.and(conf.endedDate.before(now));

		if (_past != null) {
			builder.and(conf.startedDate.after(_past));
		}

		return builder.getValue();
	}

	public static Predicate upcomingConferences() {
		QConference conf = QConference.conference;
		final Date now = new Date();
		return conf.startedDate.after(now);
	}

}

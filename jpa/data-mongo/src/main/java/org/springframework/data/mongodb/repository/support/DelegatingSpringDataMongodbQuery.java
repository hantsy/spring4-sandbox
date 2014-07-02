package org.springframework.data.mongodb.repository.support;

import org.springframework.data.mongodb.core.MongoOperations;

public class DelegatingSpringDataMongodbQuery<T> extends SpringDataMongodbQuery<T> {

	public DelegatingSpringDataMongodbQuery(MongoOperations operations,
			Class<? extends T> type) {
		super(operations, type);
	}
}

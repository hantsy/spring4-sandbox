package com.hantsylabs.example.spring.job;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.hantsylabs.example.spring.model.Conference;

public class ConferenceItemWriter extends JdbcBatchItemWriter<Conference> {

	public ConferenceItemWriter(DataSource ds) {
		super();
		setDataSource(ds);
		setSql("update conference set name=:name where id=:id");
		setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Conference>() );
	}

}

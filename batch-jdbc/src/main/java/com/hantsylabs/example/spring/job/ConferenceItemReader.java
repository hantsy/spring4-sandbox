package com.hantsylabs.example.spring.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.jdbc.core.RowMapper;

import com.hantsylabs.example.spring.model.Conference;

public class ConferenceItemReader extends JdbcPagingItemReader<Conference> {
	

	public ConferenceItemReader(DataSource ds) {
		super();
		setDataSource(ds);
		setPageSize(10);
		setQueryProvider(queryProvider(ds));
		setRowMapper(rowMapper());
	}
	
	private RowMapper<Conference> rowMapper() {
		return new RowMapper<Conference>() {
			
			@Override
			public Conference mapRow(ResultSet rs, int rowNum) throws SQLException {
				Conference conf=new Conference();
				
				conf.setId(rs.getLong("id"));
				conf.setName(rs.getString("name"));
				
				return conf;
			}
		};
	}

	private PagingQueryProvider queryProvider(DataSource ds) {
		SqlPagingQueryProviderFactoryBean queryProvider=new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(ds);
		queryProvider.setSelectClause("id, name ");
		queryProvider.setFromClause("conference");
		queryProvider.setSortKey("id");
		try {
			return queryProvider.getObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}

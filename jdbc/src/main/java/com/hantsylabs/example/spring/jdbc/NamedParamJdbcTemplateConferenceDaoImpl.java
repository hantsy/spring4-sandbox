package com.hantsylabs.example.spring.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.dao.ConferenceDao;
import com.hantsylabs.example.spring.model.Conference;

@Repository
public class NamedParamJdbcTemplateConferenceDaoImpl implements ConferenceDao {
	private static final Logger log = LoggerFactory
			.getLogger(NamedParamJdbcTemplateConferenceDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParamJdbcTemplate;

	@Override
	public Conference findById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
					"select * from conference where id =?",
					new Object[] { id }, new ConferenceMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long save(final Conference conference) {

		final String INSERT_SQL = "insert into conference (id, name, slug, description, started_date, ended_date, version) values (default, ?, ?, ?, ?, ?, 1) ";

		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL,
						new String[] { "id" });

				ps.setString(1, conference.getName());
				ps.setString(2, conference.getSlug());
				ps.setString(3, conference.getDescription());
				ps.setTimestamp(4, new java.sql.Timestamp(conference
						.getStartedDate().getTime()));
				ps.setTimestamp(5, new java.sql.Timestamp(conference
						.getEndedDate().getTime()));

				return ps;
			}
		}, generatedKeyHolder);

		return (Long) generatedKeyHolder.getKey();

	}

	@Override
	public void update(final Conference conference) {
		jdbcTemplate
				.update("update conference set slug=?, name=?, description=?, started_date=?, ended_date=? where id =? ",
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, conference.getSlug());
								ps.setString(2, conference.getName());
								ps.setString(3, conference.getDescription());
								ps.setTimestamp(4, new java.sql.Timestamp(
										conference.getStartedDate().getTime()));
								ps.setTimestamp(5, new java.sql.Timestamp(
										conference.getEndedDate().getTime()));
								ps.setLong(6, conference.getId());
							}
						});
	}

	@Override
	public void delete(final Long id) {
		jdbcTemplate.update("delete from conference where id=?", id);
	}

	@Override
	public void delete(final Conference conf) {
		jdbcTemplate.update("delete from conference where id=?", conf.getId());
	}

	private class ConferenceMapper implements RowMapper<Conference>{

		@Override
		public Conference mapRow(ResultSet rs, int rowNum) throws SQLException {
			Conference conference = new Conference();
			conference.setName(rs.getString("name"));
			conference.setDescription(rs.getString("description"));
			conference.setSlug(rs.getString("slug"));
			conference.setStartedDate(rs.getDate("started_date"));
			conference.setEndedDate(rs.getDate("ended_date"));
			conference.setId(rs.getLong("id"));
			return conference;
		}
	}

	@Override
	public void deleteAll() {
		int deleted = jdbcTemplate.update("delete from conference");
		if (log.isDebugEnabled()) {
			log.debug("rows deleted @" + deleted);
		}
	}

	@Override
	public Conference findBySlug(String slug) {
		List<Conference> confs = namedParamJdbcTemplate.query(
				"select * from conference where slug=:slug", new MapSqlParameterSource().addValue("slug", slug),
				new ConferenceMapper());
		if (!confs.isEmpty()) {
			return confs.get(0);
		}
		return null;
	}
}

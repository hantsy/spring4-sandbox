package com.hantsylabs.example.spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.dao.ConferenceDao;
import com.hantsylabs.example.spring.model.Conference;

@Repository
public class SimpleJdbcConferenceDaoImpl implements ConferenceDao {
	private static final Logger log = LoggerFactory
			.getLogger(SimpleJdbcConferenceDaoImpl.class);

	@Autowired
	DataSource dataSource;

	private static final String INSERT_SQL = "insert into conference (id, name, slug, description, started_date, ended_date, version) values (default, ?, ?, ?, ?, ?, 1) ";

	private class ConferencenJdbcInsert extends SimpleJdbcInsert {

		private Conference conference;

		public ConferencenJdbcInsert(DataSource dataSource,
				Conference conference) {
			super(dataSource);
			this.conference = conference;
			this.withTableName("conference")
					.usingColumns("name", "slug", "description",
							"started_date", "ended_date")
					.usingGeneratedKeyColumns("id");
			compile();
		}

		public Long go() {
			Map<String, Object> params = new HashMap<>();
			params.put("name", conference.getName());
			params.put("slug", conference.getSlug());
			params.put("description", conference.getDescription());
			params.put("started_date", new java.sql.Timestamp(conference
					.getStartedDate().getTime()));
			params.put("ended_date", new java.sql.Timestamp(conference
					.getEndedDate().getTime()));

			return super.executeAndReturnKey(params).longValue();
		}

	}

	private static final String SELECT_BY_ID_SQL = "select * from conference where id=?";

	private class FindById extends MappingSqlQuery<Conference> {

		private Long id;

		public FindById(DataSource ds, String sql, Long id) {
			super(ds, sql);
			this.id = id;
			declareParameter(new SqlParameter(Types.NUMERIC));
			compile();
		}

		public Conference go() {
			return super.findObject(id);
		}

		@Override
		protected Conference mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			return SimpleJdbcConferenceDaoImpl.this.mapRow(rs);
		}

	}

	private static final String SELECT_BY_SLUG_SQL = "select * from conference where slug=?";

	private class FindBySlug extends MappingSqlQuery<Conference> {

		private String slug;

		public FindBySlug(DataSource ds, String sql, String slug) {
			super(ds, sql);
			this.slug = slug;
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		public Conference go() {
			List<Conference> confs= super.execute(slug);
			if(!confs.isEmpty()){
				return confs.get(0);
			}
			return null;
		}

		@Override
		protected Conference mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			return SimpleJdbcConferenceDaoImpl.this.mapRow(rs);
		}

	}

	private static final String DELETE_BY_ID_SQL = "delete from conference where id=?";

	private class DeleteById extends SqlUpdate {

		private Long id;

		public DeleteById(DataSource ds, String sql, Long id) {
			super(ds, sql);
			this.id = id;
			declareParameter(new SqlParameter(Types.NUMERIC));
			compile();
		}

		public int go() {
			return super.update(id);
		}

	}

	private static final String DELETE_ALL_SQL = "delete from conference";

	private class DeleteAll extends SqlUpdate {

		public DeleteAll(DataSource ds, String sql) {
			super(ds, sql);
			compile();
		}

		public int go() {
			return super.update();
		}
	}

	private static final String UPDATE_SQL = "update conference set slug=?, name=?, description=?, started_date=?, ended_date=? where id =?";

	private class ConferenceUpdate extends SqlUpdate {

		private Conference conference;

		public ConferenceUpdate(DataSource ds, String sql, Conference conference) {
			super(ds, sql);
			this.conference = conference;
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.NUMERIC));
			compile();
		}

		public int go() {
			return super
					.update(conference.getSlug(), conference.getName(),
							conference.getDescription(),
							new java.sql.Timestamp(conference.getStartedDate()
									.getTime()), new java.sql.Timestamp(
									conference.getEndedDate().getTime()),
							conference.getId());
		}
	}

	@Override
	public Conference findById(Long id) {
		try {
			return new FindById(dataSource, SELECT_BY_ID_SQL, id).go();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Conference findBySlug(String slug) {
		try {
			return new FindBySlug(dataSource, SELECT_BY_SLUG_SQL, slug).go();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long save(final Conference conference) {
		return new ConferencenJdbcInsert(dataSource, conference).go();
	}

	@Override
	public void update(final Conference conference) {
		int updated = new ConferenceUpdate(dataSource, UPDATE_SQL, conference)
				.go();
		if (log.isDebugEnabled()) {
			log.debug("rows updated@" + updated);
		}
	}

	@Override
	public void delete(final Long id) {
		new DeleteById(dataSource, DELETE_BY_ID_SQL, id).go();
	}

	@Override
	public void delete(final Conference conf) {
		new DeleteById(dataSource, DELETE_BY_ID_SQL, conf.getId()).go();
	}

	@Override
	public void deleteAll() {
		int deleted = new DeleteAll(dataSource, DELETE_ALL_SQL).go();
		if (log.isDebugEnabled()) {
			log.debug("rows deleted @" + deleted);
		}
	}

	private Conference mapRow(ResultSet rs) throws SQLException {
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

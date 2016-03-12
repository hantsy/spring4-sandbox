package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import javax.batch.runtime.BatchStatus;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.config.JobConfig;
import com.hantsylabs.example.spring.config.JpaBatchConfigurer;
import com.hantsylabs.example.spring.config.JpaConfig;
import com.hantsylabs.example.spring.model.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfig.class, JpaBatchConfigurer.class, JobConfig.class })
@Transactional(transactionManager="transactionManager")
@Rollback(value=false)
public class JpaBatchTest {

	private static final Logger log = LoggerFactory.getLogger(JpaBatchTest.class);

	@Autowired
	ConferenceDao conferenceDao;

	@PersistenceContext
	EntityManager entityManager;

	@BeforeClass
	public static void initTestClass() {
		log.debug("===================before class======================");

	}

	@Before
	public void beforeTestCase() {
		log.debug("===================before test=====================");
		Long id = conferenceDao.save(newConference());
		assertTrue(id != null);
	}

	@After
	public void afterTestCase() {
		log.debug("===================after test=====================");
		conferenceDao.deleteAll();
	}

	private Conference newConference() {
		Conference conf = new Conference();
		conf.setName("JUD2013");
		conf.setSlug("jud-2013");
		conf.setDescription("JBoss User Developer Conference 2013 Boston");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 30);

		Date startedDate = cal.getTime();

		conf.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		Date endedDate = cal.getTime();
		conf.setEndedDate(endedDate);

		log.debug("new conference object:" + conf);
		return conf;
	}

	@Autowired
	private Job javaJob;

	@Autowired
	private JobLauncher jobLauncher;

	@Test
	public void jobTest() {
		try {
			JobExecution execution = jobLauncher.run(javaJob, new JobParameters());
			assertEquals(BatchStatus.COMPLETED, execution.getStatus());
			
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}

		Conference conf = conferenceDao.findById(1L);

		log.debug("conf@" + conf);
	}

}

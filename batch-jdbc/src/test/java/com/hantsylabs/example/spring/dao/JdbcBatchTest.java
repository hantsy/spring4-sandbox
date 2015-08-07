package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
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
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.config.JdbcConfig;
import com.hantsylabs.example.spring.config.JobConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JdbcConfig.class, JobConfig.class })
@Rollback()
public class JdbcBatchTest {

	private static final Logger log = LoggerFactory.getLogger(JdbcBatchTest.class);

	@Autowired
	ConferenceDao conferenceDao;

	@BeforeClass
	public static void initTestClass() {
		log.debug("===================before class======================");

	}

	@Before
	@Transactional
	public void beforeTestCase() {
		log.debug("===================before test=====================");
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("===================after test=====================");
		conferenceDao.deleteAll();
	}

	@Autowired
	private Job validJob;

	@Autowired
	private JobLauncher jobLauncher;

	@Test
	public void jobTest() {

		JobParametersBuilder builder = new JobParametersBuilder();
		try {
			JobExecution execution = jobLauncher.run(validJob, builder.toJobParameters());
			assertEquals(BatchStatus.COMPLETED, execution.getStatus());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}

	}

}

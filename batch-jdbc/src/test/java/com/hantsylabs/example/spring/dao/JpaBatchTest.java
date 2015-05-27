package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

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
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.config.JdbcConfig;
import com.hantsylabs.example.spring.config.JobConfig;
import com.hantsylabs.example.spring.model.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JdbcConfig.class,  JobConfig.class })
@TransactionConfiguration()
public class JpaBatchTest {

	private static final Logger log = LoggerFactory
			.getLogger(JpaBatchTest.class);

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
	//	Long id = conferenceDao.save(newConference());
	//	assertTrue(id != null);
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("===================after test=====================");
		conferenceDao.deleteAll();
	}

//	private Conference newConference() {
//		Conference conf = new Conference();
//		conf.setName("JUD2013");
//		conf.setSlug("jud-2013");
//		conf.setDescription("JBoss User Developer Conference 2013 Boston");
//
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_YEAR, 30);
//
//		Date startedDate = cal.getTime();
//
//		conf.setStartedDate(startedDate);
//
//		cal.add(Calendar.DAY_OF_YEAR, 7);
//
//		Date endedDate = cal.getTime();
//		conf.setEndedDate(endedDate);
//
//		log.debug("new conference object:" + conf);
//		return conf;
//	}


	@Autowired
	private Job validJob;

	@Autowired
	private JobLauncher jobLauncher;

	@Test
	//@Transactional
	public void jobTest() {
		//Long id = conferenceDao.save(newConference());
		JobParametersBuilder builder=new JobParametersBuilder();
		try {
			jobLauncher.run(validJob, builder.toJobParameters());
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Conference conf = conferenceDao.findById(1L);

		log.debug("conf@" + conf);
	}

}

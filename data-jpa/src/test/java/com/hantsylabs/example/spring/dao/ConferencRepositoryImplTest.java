package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.jpa.ConferenceRepository;
import com.hantsylabs.example.spring.jpa.spec.JpaSpecs;
import com.hantsylabs.example.spring.jpa.spec.QueryDslPredicates;
import com.hantsylabs.example.spring.model.Address;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.QConference;
import com.hantsylabs.example.spring.model.Signup;
import com.hantsylabs.example.spring.model.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/hantsylabs/example/spring/config/applicationContext-jpa.xml")
@TransactionConfiguration()
public class ConferencRepositoryImplTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConferencRepositoryImplTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;

	@PersistenceContext
	EntityManager em;

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

		conf.setAddress(newAddress());

		log.debug("new conference object:" + conf);
		return conf;
	}

	private Address newAddress() {
		Address address = new Address();
		address.setAddressLine1("address line 1");
		address.setAddressLine2("address line 2");
		address.setCity("NY");
		address.setCountry("CN");
		address.setZipCode("510000");

		return address;
	}

	private Signup newSignup() {
		Signup signup = new Signup();

		signup.setComment("test comments");
		signup.setCompany("TestCompany");
		signup.setCreatedDate(new Date());
		signup.setEmail("test@test.com");
		signup.setFirstName("Hantsy");
		signup.setLastName("Bai");
		signup.setOccupation("Developer");
		signup.setPhone("123 222 444");
		signup.setStatus(Status.PENDING);

		return signup;
	}

	@BeforeClass
	public static void init() {
		log.debug("==================before class=========================");

	}

	@Before
	@Transactional
	public void beforeTestCase() {
		log.debug("==================before test case=========================");
		conferenceRepository.save(newConference());
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		conferenceRepository.deleteAll();
		em.clear();
	}

	@Test
	@Transactional
	public void retrieveConference() {
		Conference conference = newConference();
		conference.setSlug("test-jud");
		conference.setName("Test JUD");
		conference.getAddress().setCountry("US");
		conference = conferenceRepository.save(conference);
		em.flush();

		assertTrue(null != conference.getId());

		conference = conferenceRepository.findBySlug("test-jud");
		assertTrue(null != conference);

		List<Conference> confs = conferenceRepository
				.findByAddressCountry("US");
		assertTrue(!confs.isEmpty());

		confs = conferenceRepository.searchByConferenceName("Test JUD");
		assertTrue(!confs.isEmpty());

		confs = conferenceRepository.searchByNamedConferenceName("Test JUD");
		assertTrue(!confs.isEmpty());

		confs = conferenceRepository.searchByMyNamedQuery("Test JUD");
		assertTrue(!confs.isEmpty());

		confs = conferenceRepository.searchByDescription("Boston");
		assertTrue(!confs.isEmpty());

		confs = conferenceRepository.findByDescriptionLike("%Boston%");
		assertTrue(!confs.isEmpty());

	}

	@Test
	@Transactional
	public void retrieveConferenceQueryDSL() {
		Conference conference = newConference();
		conference.setSlug("test-jud");
		conference.setName("Test JUD");
		conference.getAddress().setCountry("US");

		conference.addSignup(newSignup());
		conference = conferenceRepository.save(conference);
		em.flush();

		assertTrue(null != conference.getId());
		em.clear();
		conference = conferenceRepository.findBySlug("test-jud");
		assertTrue(null != conference);

		List<Conference> confs = (List<Conference>) conferenceRepository
				.findAll(QConference.conference.address.country.eq("US"));
		assertTrue(!confs.isEmpty());
		em.clear();
		confs = (List<Conference>) conferenceRepository
				.findAll(QConference.conference.name.eq("Test JUD"));
		assertTrue(!confs.isEmpty());
		em.clear();
		confs = (List<Conference>) conferenceRepository
				.findAll(QConference.conference.description.contains("Boston"));
		assertTrue(!confs.isEmpty());
		em.clear();
		confs = (List<Conference>) conferenceRepository
				.findAll(QConference.conference.signups.any().email
						.eq("test@test.com"));
		assertTrue(!confs.isEmpty());
		em.clear();
		confs = (List<Conference>) conferenceRepository
				.findAll(QConference.conference.signups.any().email
						.eq("test@test.com123"));
		assertTrue(confs.isEmpty());
	}

	@Test
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateConference() {
		Conference conference = newConference();
		conference.setSlug("test-jud");
		conference.setName("Test JUD");
		conference.getAddress().setCountry("US");
		conference = conferenceRepository.save(conference);
		em.flush();
		em.clear();

		Long id = conference.getId();

		log.debug("saved conference id @" + id);
		assertTrue(null != id);

		try {

			conferenceRepository.modifyConferenceDescrition("Mydesc", id);
			em.flush();em.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Conference conf = conferenceRepository.findOne(id);
		log.debug("updated conference @" + conf);

		assertTrue("Mydesc".equals(conf.getDescription()));

	}

	@Test
	@Transactional
	public void retrieveConferenceByDate() {
		Conference conference = newConference();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -30);

		Date startedDate = cal.getTime();

		conference.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		Date endedDate = cal.getTime();
		conference.setEndedDate(endedDate);
		conference = conferenceRepository.save(conference);
		em.flush();

		conference = newConference();

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);

		startedDate = cal.getTime();

		conference.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		endedDate = cal.getTime();
		conference.setEndedDate(endedDate);
		conference = conferenceRepository.save(conference);
		em.flush();

		conference = newConference();

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 30);

		startedDate = cal.getTime();

		conference.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		endedDate = cal.getTime();
		conference.setEndedDate(endedDate);
		conference = conferenceRepository.save(conference);
		em.flush();
		em.clear();

		List<Conference> confs = conferenceRepository.findAll(JpaSpecs
				.inProgressConferences());
		assertTrue(confs.size() == 1);

		confs = conferenceRepository.findAll(JpaSpecs.pastConferences(null));
		assertTrue(confs.size() == 1);

		confs = conferenceRepository.findAll(JpaSpecs.upcomingConferences());
		assertTrue(confs.size() == 2);
	}

	@Test
	@Transactional
	public void retrieveConferenceByDateQueryDSL() {
		Conference conference = newConference();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -30);

		Date startedDate = cal.getTime();

		conference.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		Date endedDate = cal.getTime();
		conference.setEndedDate(endedDate);
		conference = conferenceRepository.save(conference);
		em.flush();

		conference = newConference();

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);

		startedDate = cal.getTime();

		conference.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		endedDate = cal.getTime();
		conference.setEndedDate(endedDate);
		conference = conferenceRepository.save(conference);
		em.flush();

		conference = newConference();

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 30);

		startedDate = cal.getTime();

		conference.setStartedDate(startedDate);

		cal.add(Calendar.DAY_OF_YEAR, 7);

		endedDate = cal.getTime();
		conference.setEndedDate(endedDate);
		conference = conferenceRepository.save(conference);
		em.flush();		
		em.clear();

		List<Conference> confs = (List<Conference>) conferenceRepository
				.findAll(QueryDslPredicates.inProgressConferences());
		assertTrue(confs.size() == 1);

		confs = (List<Conference>) conferenceRepository
				.findAll(QueryDslPredicates.pastConferences(null));
		assertTrue(confs.size() == 1);

		confs = (List<Conference>) conferenceRepository
				.findAll(QueryDslPredicates.upcomingConferences());
		assertTrue(confs.size() == 2);
	}

}

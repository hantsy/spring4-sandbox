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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hantsylabs.example.spring.jpa.AuditorBean;
import com.hantsylabs.example.spring.jpa.ConferenceRepository;
import com.hantsylabs.example.spring.jpa.SignupRepository;
import com.hantsylabs.example.spring.jpa.UserRepository;
import com.hantsylabs.example.spring.model.Address;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.Signup;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/hantsylabs/example/spring/config/applicationContext-jpa.xml")
@TransactionConfiguration()
public class ConferenceTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConferenceTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;

	@PersistenceContext
	EntityManager em;

	@Autowired
	AuditorBean auditorBean;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SignupRepository signupRepository;

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
		User user = userRepository.save(new User("hantsy"));
		log.debug("user id @" + user.getId());
		auditorBean.setCurrentAuditor(user);
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

		log.debug("conference @" + conference);
		assertTrue(null != conference);

		assertTrue(conference.getCreatedBy() != null);
		assertTrue("hantsy".equals(conference.getCreatedBy().getUsername()));
		assertTrue(conference.getCreatedDate() != null);
		assertTrue(conference.getLastModifiedBy() != null);

		assertTrue("hantsy"
				.equals(conference.getLastModifiedBy().getUsername()));
		assertTrue(conference.getLastModifiedDate() != null);

		assertTrue(conference.getCreatedDate().equals(
				conference.getLastModifiedDate()));

		conference
				.setDescription("change desc, the modified date should be updated.");
		conferenceRepository.save(conference);
		em.flush();

		conference = conferenceRepository.findBySlug("test-jud");
		log.debug("created date @" + conference.getCreatedDate());
		log.debug("modified date @" + conference.getLastModifiedDate());
		assertTrue(!conference.getCreatedDate().equals(
				conference.getLastModifiedDate()));
	}

	@Test
	@Transactional
	public void testSignup() {
		Conference conference = newConference();
		conference.setSlug("test-jud");
		conference.setName("Test JUD");
		conference.getAddress().setCountry("US");
		Signup signup = newSignup();
		conference.addSignup(signup);
		conference = conferenceRepository.save(conference);
		em.flush();

		assertTrue(null != conference.getId());
		assertTrue(null != signup.getId());

		Signup signup2=signupRepository.findById(signup.getId());
		
		assertTrue(signup2.getCreatedBy() != null);
		assertTrue("hantsy".equals(signup2.getCreatedBy().getUsername()));
		assertTrue(signup2.getCreatedDate() != null);
		assertTrue(signup2.getModifiedBy() != null);

		assertTrue("hantsy"
				.equals(signup2.getModifiedBy().getUsername()));
		assertTrue(signup2.getModifiedDate() != null);

		assertTrue(signup2.getCreatedDate().equals(
				signup2.getModifiedDate()));

		signup2.setComment("add comment to signup, and the signup is changed, the modified date should be updated.");
		signupRepository.save(signup2);
		em.flush();

		signup2=signupRepository.findById(signup.getId());
		log.debug("created date @" + signup2.getCreatedDate());
		log.debug("modified date @" + signup2.getModifiedDate());
		assertTrue(!signup2.getCreatedDate().equals(
				signup2.getModifiedDate()));
	}

}

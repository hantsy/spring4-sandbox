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
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.hantsylabs.example.spring.jpa.ConferenceRepository;
import com.hantsylabs.example.spring.jpa.SignupRepository;
import com.hantsylabs.example.spring.jpa.UserRepository;
import com.hantsylabs.example.spring.model.Address;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.Signup;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/com/hantsylabs/example/spring/config/applicationContext-jpa-envers.xml")
@TransactionConfiguration()
public class SignupEnversTest {
	private static final Logger log = LoggerFactory
			.getLogger(SignupEnversTest.class);

	@Autowired
	private ConferenceRepository conferenceRepository;

	@PersistenceContext
	EntityManager em;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SignupRepository signupRepository;

	@Autowired
	PlatformTransactionManager transactionManager;

	TransactionTemplate transactionTemplate;

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

	User user;

	@Before
	@Transactional
	public void beforeTestCase() {
		log.debug("==================before test case=========================");

		transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@After
	@Transactional
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		signupRepository.deleteAll();
		em.flush();
	}

	@Test
	// @Transactional
	public void retrieveSignupRevision() {

		final Signup signup = newSignup();

		final Signup signup2 = transactionTemplate
				.execute(new TransactionCallback<Signup>() {

					@Override
					public Signup doInTransaction(TransactionStatus arg0) {
						signupRepository.save(signup);
						em.flush();
						return signup;
					}
				});

		// modifying description
		assertTrue(null != signup2.getId());

		log.debug("@Signup @" + signup2);
		assertTrue(null != signup2);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Revisions<Integer, Signup> revision = signupRepository
						.findRevisions(signup2.getId());

				assertTrue(!revision.getContent().isEmpty());

				Revision<Integer, Signup> lastRevision = signupRepository
						.findLastChangeRevision(signup2.getId());
				
				assertTrue(lastRevision.getRevisionNumber()==1);

			}
		});

	}

}

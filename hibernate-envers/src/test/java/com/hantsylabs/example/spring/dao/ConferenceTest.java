package com.hantsylabs.example.spring.dao;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.hantsylabs.example.spring.config.DataJpaConfig;
import com.hantsylabs.example.spring.jpa.ConferenceRepository;
import com.hantsylabs.example.spring.jpa.SignupRepository;
import com.hantsylabs.example.spring.jpa.UserRepository;
import com.hantsylabs.example.spring.model.Address;
import com.hantsylabs.example.spring.model.Conference;
import com.hantsylabs.example.spring.model.ConferenceRevisionEntity;
import com.hantsylabs.example.spring.model.SecurityUtils;
import com.hantsylabs.example.spring.model.Signup;
import com.hantsylabs.example.spring.model.Status;
import com.hantsylabs.example.spring.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataJpaConfig.class)
// @Rollback(false)
// @Transactional(transactionManager="transactionManager")
@TransactionConfiguration
public class ConferenceTest {
	private static final Logger log = LoggerFactory.getLogger(ConferenceTest.class);

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
	public void beforeTestCase() {
		log.debug("==================before test case=========================");
		user = userRepository.save(new User("hantsy"));
		log.debug("user id @" + user.getId());
		SecurityUtils.user = user;
		conferenceRepository.save(newConference());

		transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@After
	public void afterTestCase() {
		log.debug("==================after test case=========================");
		conferenceRepository.deleteAll();
		// userRepository.deleteAll();
		// em.flush();
	}

	@Test
	public void retrieveConference() {

		final Conference conference1 = newConference();

		Conference conf1 = transactionTemplate.execute(new TransactionCallback<Conference>() {

			@Override
			public Conference doInTransaction(TransactionStatus arg0) {
				conference1.setSlug("test-jud");
				conference1.setName("Test JUD");
				conference1.getAddress().setCountry("US");
				Conference reference = conferenceRepository.save(conference1);
				// em.flush();

				return reference;
			}
		});

		// modifying description
		assertTrue(null != conf1.getId());
		final Conference conference2 = conferenceRepository.findBySlug("test-jud");

		log.debug("@conference @" + conference2);
		assertTrue(null != conference2);

		final Conference conf2 = transactionTemplate.execute(new TransactionCallback<Conference>() {

			@Override
			public Conference doInTransaction(TransactionStatus arg0) {
				conference2.setDescription("changing description...");
				Conference result = conferenceRepository.save(conference2);
				// em.flush();
				return result;
			}
		});

		log.debug("@conf2 @" + conf2);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuditReader reader = AuditReaderFactory.get(em);

				List<Number> revisions = reader.getRevisions(Conference.class, conf2.getId());

				log.debug("@rev numbers@" + revisions);

				assertTrue(!revisions.isEmpty());
				Conference rev1 = reader.find(Conference.class, conf2.getId(), revisions.get(revisions.size() - 1));

				log.debug("@rev 1@" + rev1);
				assertTrue(rev1.getSlug().equals("test-jud"));
			}
		});

	}

	@Test
	public void testCustomizedRevisionEntity() {

		final Conference conference1 = newConference();

		Conference conf1 = transactionTemplate.execute(new TransactionCallback<Conference>() {

			@Override
			public Conference doInTransaction(TransactionStatus arg0) {
				conference1.setSlug("test-jud-testCustomizedRevisionEntity");
				conference1.setName("Test JUD");
				conference1.getAddress().setCountry("US");
				Conference reference = conferenceRepository.save(conference1);
				return reference;
			}
		});

		// modifying description
		assertTrue(null != conf1.getId());
		final Conference conference2 = conferenceRepository.findBySlug("test-jud-testCustomizedRevisionEntity");

		log.debug("@conference @" + conference2);
		assertTrue(null != conference2);

		final Conference conf2 = transactionTemplate.execute(new TransactionCallback<Conference>() {

			@Override
			public Conference doInTransaction(TransactionStatus arg0) {
				conference2.setDescription("changing description...");
				Conference result = conferenceRepository.save(conference2);
				return result;
			}
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				AuditReader reader = AuditReaderFactory.get(em);

				List<Number> revisions = reader.getRevisions(Conference.class, conf2.getId());

				log.debug("@rev numbers@" + revisions);
				assertTrue(!revisions.isEmpty());

				ConferenceRevisionEntity entity = em.find(ConferenceRevisionEntity.class,
						revisions.get(revisions.size() - 1));

				log.debug("@rev 1@" + entity);
				assertTrue(entity.getAuditor().getId().equals(user.getId()));

			}
		});
	}

	@Test
	public void testPropertyModification() {

		final Conference conference1 = newConference();

		Conference conf1 = transactionTemplate.execute(new TransactionCallback<Conference>() {

			@Override
			public Conference doInTransaction(TransactionStatus arg0) {
				conference1.setSlug("test-jud-testPropertyModification");
				conference1.setName("Test JUD");
				conference1.getAddress().setCountry("US");
				Conference reference = conferenceRepository.save(conference1);
				//em.flush();

				return reference;
			}
		});

		// modifying description
		assertTrue(null != conf1.getId());
		final Conference conference2 = conferenceRepository.findBySlug("test-jud-testPropertyModification");

		log.debug("@conference @" + conference2);
		assertTrue(null != conference2);

		final Conference conf2 = transactionTemplate.execute(new TransactionCallback<Conference>() {

			@Override
			public Conference doInTransaction(TransactionStatus arg0) {
				conference2.setDescription("changing description...");
				Conference result = conferenceRepository.save(conference2);
				//em.flush();

				return result;
			}
		});
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				AuditReader reader = AuditReaderFactory.get(em);

				List<Number> revisions = reader.getRevisions(Conference.class, conf2.getId());

				log.debug("@rev numbers@" + revisions);
				assertTrue(!revisions.isEmpty());
				
				List list = reader.createQuery().forEntitiesAtRevision(Conference.class, revisions.get(0))
						.add(AuditEntity.id().eq(conf2.getId())).add(AuditEntity.property("description").hasChanged())
						.getResultList();

				log.debug("@description list changed@" + list.size());
				assertTrue(!list.isEmpty());

				List slugList = reader.createQuery().forEntitiesAtRevision(Conference.class, revisions.get(0))
						.add(AuditEntity.id().eq(conf2.getId())).add(AuditEntity.property("slug").hasChanged())
						.getResultList();

				log.debug("@slugList 1@" + slugList.size());
				assertTrue(!slugList.isEmpty());

				list = reader.createQuery().forEntitiesAtRevision(Conference.class, revisions.get(1))
						.add(AuditEntity.id().eq(conf2.getId())).add(AuditEntity.property("description").hasChanged())
						.getResultList();

				log.debug("@description list changed@" + list.size());
				assertTrue(!list.isEmpty());

				slugList = reader.createQuery().forEntitiesAtRevision(Conference.class, revisions.get(1))
						.add(AuditEntity.id().eq(conf2.getId())).add(AuditEntity.property("slug").hasChanged())
						.getResultList();

				log.debug("@slugList 1@" + slugList.size());
				assertTrue(slugList.isEmpty());
			}
		});

	}
}

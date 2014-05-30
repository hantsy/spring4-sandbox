package com.hantsylabs.example.spring.annotation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hantsylabs.example.spring.model.Signup;

@TransactionalRepository
public class SignupRepository {
	
	@PersistenceContext 
	private EntityManager em;

	public Long save(Signup entity) {
		em.persist(entity);
		return entity.getId();
	}
	
	public Signup findById(Long id) {
		return em.find(Signup.class, id);
	}
	
}
	
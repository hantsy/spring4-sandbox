package com.hantsylabs.example.spring.jpa;


import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;

import com.hantsylabs.example.spring.model.User;


@Named(value="auditorBean")
public class AuditorBean implements AuditorAware<User> {
	private static final Logger LOGGER=LoggerFactory.getLogger(AuditorBean.class);

	private User currentAuditor;
	
	@Override
	public User getCurrentAuditor() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//	    if (authentication == null || !authentication.isAuthenticated()) {
//	      return null;
//	    }
//
//	    return ((MyUserDetails) authentication.getPrincipal()).getUser();
		LOGGER.debug("call AuditorAware.getCurrentAuditor(");
		return currentAuditor;
	}

	public void setCurrentAuditor(User currentAuditor) {
		this.currentAuditor = currentAuditor;
	}
	
	
	
}

package com.hantsylabs.example.spring.faces;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLoggingPhaseListener implements PhaseListener {
	private static final Logger log = LoggerFactory.getLogger(RequestLoggingPhaseListener.class);

	@Override
	public void afterPhase(PhaseEvent event) {
		log.debug("after phase:" + event.getPhaseId());
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		log.debug("before phase:" + event.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}

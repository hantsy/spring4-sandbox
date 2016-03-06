package com.hantsylabs.example.spring.faces;

import com.hantsylabs.example.spring.web.TaskNotFoundException;
import com.sun.faces.context.FacesFileNotFoundException;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    private ExceptionHandler wrapped;

    public DefaultExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
    	log.debug("invoking custom ExceptionHandlder...");
        Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
        
        while (events.hasNext()) {
            ExceptionQueuedEvent event = events.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            log.debug("Exception@" + t);
            log.debug("ExceptionHandlder began.");
            log.debug("t instanceof FacesException@" + (t instanceof FacesException));
            log.debug("t instanceof FacesFileNotFoundException@" + (t instanceof FacesFileNotFoundException));
            if (t instanceof ViewExpiredException) {
                try {
                    handleViewExpiredException((ViewExpiredException) t);
                } finally {
                    events.remove();
                }
            }

            if (t instanceof FacesFileNotFoundException|| t instanceof TaskNotFoundException) {
                try {
                    handleNotFoundException((Exception) t);
                } finally {
                    events.remove();
                }
            }
            log.debug("ExceptionHandlder end.");
            getWrapped().handle();
        }
        

    }

    private void handleViewExpiredException(ViewExpiredException vee) {
        log.debug(" handling viewExpiredException...");
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = vee.getViewId();
        log.debug("view id @" + viewId);
        NavigationHandler nav =
                context.getApplication().getNavigationHandler();
        nav.handleNavigation(context, null, viewId);
        context.renderResponse();
    }

    private void handleNotFoundException(Exception e) {
        log.debug("handling exception:...");
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = "/error.xhtml";
        log.debug("view id @" + viewId);
        NavigationHandler nav =
                context.getApplication().getNavigationHandler();
        nav.handleNavigation(context, null, viewId);
        context.getViewRoot().getViewMap(true).put("ex", e);
        context.renderResponse();
    }
}

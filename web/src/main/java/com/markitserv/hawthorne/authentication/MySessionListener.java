package com.markitserv.hawthorne.authentication;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		final HttpSession session = event.getSession();
		final ServletContext context = session.getServletContext();
    	context.setAttribute(session.getId(), session);
    	System.out.println("New Session created = " +session.getId());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		final HttpSession session = se.getSession();
    	final ServletContext context = session.getServletContext();
    	System.out.println("Session destroyed for JSESSIONID " +session.getId());
    	context.removeAttribute(session.getId());

	}

}

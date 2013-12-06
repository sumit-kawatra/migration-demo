package com.markitserv.msws.beans;

import java.io.Serializable;

/**
 * Intended to be passed around to messages when they need context
 * 
 * @author roy.truelove
 * 
 */
public class MswsRequestContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private RequestInfo request;
	private SessionInfo session;

	public RequestInfo getRequest() {
		return request;
	}

	public void setRequest(RequestInfo request) {
		this.request = request;
	}

	public SessionInfo getSession() {
		return session;
	}

	public void setSession(SessionInfo session) {
		this.session = session;
	}

}

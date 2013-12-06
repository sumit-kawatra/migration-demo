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

	public RequestInfo getRequestInfo() {
		return request;
	}

	public void setRequestInfo(RequestInfo request) {
		this.request = request;
	}

	public SessionInfo getSessionInfo() {
		return session;
	}

	public void setSessionInfo(SessionInfo session) {
		this.session = session;
	}

}

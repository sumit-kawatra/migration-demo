package com.markitserv.msws.beans;

import java.io.Serializable;
import java.util.Set;

/**
 * Wraps session info, since it won't be exposed by spring outside of a web req
 * thread
 * 
 * @author roy.truelove
 * 
 */
public class SessionInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	public class UserData {

		private String userName;
		private Set<String> roles;

		public Set<String> getRoles() {
			return roles;
		}

		public void setRoles(Set<String> roles) {
			this.roles = roles;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	public class SessionData {

		private int sessionTtl;

		public int getTtl() {
			return sessionTtl;
		}

		public void setTtl(int sessionTtl) {
			this.sessionTtl = sessionTtl;
		}
	}

	private UserData userData;
	private SessionData sessionData;

	public SessionInfo() {

		userData = new UserData();
		sessionData = new SessionData();

	}

	public void setTtl(int ttl) {
		sessionData.sessionTtl = ttl;
	}

	public void setRoles(Set<String> roles) {
		userData.setRoles(roles);
	}

	public void setUsername(String userName) {
		userData.setUserName(userName);
	}

	public UserData getUser() {
		return userData;
	}

	public SessionData getSession() {
		return sessionData;
	}

}

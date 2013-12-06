package com.markitserv.msws.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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

	private String userName;
	private Set<String> roles;
	private int sessionTtl;
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public int getSessionTtl() {
		return sessionTtl;
	}

	public void setSessionTtl(int sessionTtl) {
		this.sessionTtl = sessionTtl;
	}

	public <T> void setAttribute(String key, T value) {
		this.attributes.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Class<T> clazz) {
		return (T) this.attributes.get(key);
	}

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	public boolean isAttributeSet(String key) {
		return attributes.containsKey(key);
	}

	public void addAllAttributes(Map<String, Object> attribs) {
		this.attributes.putAll(attribs);
	}
}

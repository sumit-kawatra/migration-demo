package com.markitserv.hawthorne.types;

import java.util.List;

import com.google.common.base.Objects;
import com.markitserv.msws.Type;

public class SessionInfo extends Type {

	private String username;
	private List<String> roles;
	private String participantName;
	private String fullName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	// NOTE that this isn't the best way to do this, need to revisit
	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(username, roles, participantName, fullName);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}
}

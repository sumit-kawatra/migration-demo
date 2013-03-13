/**
 * 
 */
package com.markitserv.hawthorne.types;

import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.markitserv.msws.Type;

/**
 * @author kiran.gogula
 * 
 */
public class InterestGroup extends Type {
	private int id;
	private String name;
	private String shortName;
	private boolean active;
	private Set<User> users;
	private int participantId;

	public InterestGroup(int id1, String name1) {
		id = id1;
		name = name1;
	}

	public InterestGroup() {
		// TODO Auto-generated constructor stub
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name, shortName, active, users, participantId);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}

}

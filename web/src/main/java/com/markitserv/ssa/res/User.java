package com.markitserv.ssa.res;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.rest.RestReference;
import com.markitserv.rest.RestReferenceSerializer;

public class User {

	private long id;
	private Participant participant;
	private String firstName;
	private String lastName;
	private String password;
	private boolean isActive;
	private boolean isSuperUser;
	private Collection<UserBook> userBooks;

	public boolean isSuperUser() {
		return isSuperUser;
	}

	public void setSuperUser(boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

	public User() {
		super();
	}

	public User(long id, String firstName, String lastName, Participant company) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setParticipant(company);
		this.setActive(true);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@RestReference
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant company) {
		this.participant = company;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public Collection<UserBook> getUserBooks() {
		return this.userBooks;
	}

	public void setUserBooks(Collection<UserBook> userBooks) {
		this.userBooks = userBooks;
	}
} 
package com.markitserv.ssa.res;

import com.markitserv.rest.RestLink;

public class User {
	
	private long id;
	private Participant participant;
	private String firstName;
	private String lastName;
	boolean isActive;

	public User() {
		super();
	}
	
	public User(long id, String firstName, String lastName, Participant company) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.setCompany(company);
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
	public Participant getCompany() {
		return participant;
	}
	public void setCompany(Participant company) {
		this.participant = company;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}

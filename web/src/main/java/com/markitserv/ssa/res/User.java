package com.markitserv.ssa.res;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.markitserv.rest.RESTResource;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@RESTResource
public class User {
	
	public User() {
		super();
	}
	
	public User(long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	private long id;
	private String firstName;
	private String lastName;
	
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
}

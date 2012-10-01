package com.markitserv.ssa.res;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.markitserv.rest.RESTResource;

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
		this.someBalony = "Shouldn't be here";
	}
	private long id;
	private String firstName;
	private String lastName;
	private String someBalony;
	
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

	@JsonIgnore 
	public String getSomeBalony() {
		return someBalony;
	}

	public void setSomeBalony(String someBalony) {
		this.someBalony = someBalony;
	}
}

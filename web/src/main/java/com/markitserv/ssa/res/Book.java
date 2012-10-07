package com.markitserv.ssa.res;

import com.markitserv.rest.RestLink;

public class Book {

	String name;
	long id;

	public Book() {
		super();
	}

	public Book(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package com.markitserv.ssa.res;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.markitserv.rest.RestLink;

public class Participant {
	
	Logger log = LoggerFactory.getLogger(Participant.class);
	
	private long id;
	private String name;
	private Collection<Book> books;
	private Collection<User> users;
	
	public Participant(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Participant() {
		super();
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
	public void setBooks(Collection<Book> availableBooks) {
		this.books = availableBooks;
	}
	
	@RestLink(relUri = "books")
	public Collection<Book> getBooks() {
		log.info("Books did get called");
		return this.books;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	@RestLink(relUri = "users")
	public Collection<User> getUsers() {
		return users;
	}
} 
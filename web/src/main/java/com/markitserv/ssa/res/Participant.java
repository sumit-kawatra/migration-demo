package com.markitserv.ssa.res;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.rest.LinkSerializer;

//@JsonSerialize(using=LinkSerializer.class)
public class Participant {
	
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
	
	public Collection<Book> getBooks() {
		return this.books;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	public Collection<User> getUsers() {
		return users;
	}
}

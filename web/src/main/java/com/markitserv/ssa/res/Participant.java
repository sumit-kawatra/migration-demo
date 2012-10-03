package com.markitserv.ssa.res;

import java.util.Collection;
import java.util.Map;

public class Participant {
	
	private long id;
	private String name;
	private Collection<Book> books;
	
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
}

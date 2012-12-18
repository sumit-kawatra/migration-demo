package com.markitserv.hawthorne.res;

import com.markitserv.rest.RestReference;

public class Book {

	private String name;
	private long id;
	private Participant participant;

	@RestReference
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Book() {
		super();
	}

	public Book(long id, String name, Participant participant) {
		super();
		this.id = id;
		this.name = name;
		this.participant = participant;
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

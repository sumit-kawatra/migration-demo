package com.markitserv.hawthorne.res;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Participant {

	Logger log = LoggerFactory.getLogger(Participant.class);

	private long id;
	private String name;
	private Collection<Book> books;
	private Collection<User> users;
	private Collection<LegalEntity> legalEntities;
	private Collection<BrokerCode> brokerCodes;

	public Participant() {
		super();
	}

	public Participant(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@JsonIgnore
	public Collection<Book> getBooks() {
		return this.books;
	}

	@JsonIgnore
	public Collection<BrokerCode> getBrokerCodes() {
		return brokerCodes;
	}

	public long getId() {
		return id;
	}

	@JsonIgnore
	public Collection<LegalEntity> getLegalEntities() {
		return legalEntities;
	}

	public String getName() {
		return name;
	}

	@JsonIgnore
	public Collection<User> getUsers() {
		return users;
	}

	public void setBooks(Collection<Book> availableBooks) {
		this.books = availableBooks;
	}

	public void setBrokerCodes(Collection<BrokerCode> brokerCodes) {
		this.brokerCodes = brokerCodes;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setLegalEntities(Collection<LegalEntity> legalEntities) {
		this.legalEntities = legalEntities;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
}
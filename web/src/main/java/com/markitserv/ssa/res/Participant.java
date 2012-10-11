package com.markitserv.ssa.res;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.markitserv.rest.RestReference;

public class Participant {

	Logger log = LoggerFactory.getLogger(Participant.class);

	private long id;
	private String name;
	private Collection<Book> books;
	private Collection<User> users;
	private Collection<LegalEntity> legalEntities;
	private Collection<BrokerCode> brokerCodes;

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

	@JsonIgnore
	public Collection<Book> getBooks() {
		log.info("Books did get called");
		return this.books;
	}

	@JsonProperty(value = "books")
	public RestReference getBooksRef() {
		return new RestReference().withUri("books");
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@JsonIgnore
	public Collection<User> getUsers() {
		return users;
	}

	@JsonProperty(value = "users")
	public RestReference getUsersRef() {
		return new RestReference().withUri("users");
	}
	
	@JsonIgnore
	public Collection<LegalEntity> getLegalEntities() {
		return legalEntities;
	}
	
	@JsonProperty(value = "legalEntities")
	public RestReference getLegalEntitiesRef() {
		return new RestReference().withUri("legalEntities");
	}

	public void setLegalEntities(Collection<LegalEntity> legalEntities) {
		this.legalEntities = legalEntities;
	}

	public void setBrokerCodes(Collection<BrokerCode> brokerCodes) {
		this.brokerCodes = brokerCodes;
	}


}
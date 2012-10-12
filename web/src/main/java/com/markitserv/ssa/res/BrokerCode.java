package com.markitserv.ssa.res;

import com.markitserv.rest.RestReference;

public class BrokerCode {

	private long id;
	private String name;
	private Participant participant;
	
	public BrokerCode(long id, String name, Participant participant) {
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
	
	@RestReference
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
} 
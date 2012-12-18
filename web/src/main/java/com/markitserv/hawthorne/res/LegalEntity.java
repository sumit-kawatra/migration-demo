package com.markitserv.hawthorne.res;

import com.markitserv.rest.RestReference;

public class LegalEntity {
	
	private long id;
	private Participant participant;
	private String name;
	
	public LegalEntity(long id, Participant participant, String name) {
		super();
		this.id = id;
		this.participant = participant;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@RestReference
	public Participant getParticipant() {
		return participant;
	}
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

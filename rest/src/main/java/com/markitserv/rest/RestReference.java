package com.markitserv.rest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = RestReferenceSerializer.class)
public class RestReference {

	private long id;
	private String uri;

	public RestReference withUri(String uri) {
		this.uri = uri;
		return this;
	}
	
	public RestReference withId(long id) {
		this.id = id;
		return this;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}

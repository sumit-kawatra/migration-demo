package com.markitserv.rest;

public class RESTEnvelopeMaker {
	
	private String baseURL;

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public <T> RESTEnvelope<T> makeEnvelope(T payload) {
		return new RESTEnvelope<T>(baseURL, payload);
	}
}

package com.markitserv.rest;

public class RESTEnvelope<T> {
	
	private T payload;
	private String baseURL;
	
	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public RESTEnvelope(String baseURL, T payload) {
		this.baseURL = baseURL;
		this.payload = payload;
	}

	T getPayload() {
		return payload;
	}
}

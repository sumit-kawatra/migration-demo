package com.markitserv.rest;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//@JsonSerialize(using=RestReferenceSerializer.class)
public class RestReference {
	
	private InnerRef innerRef = new InnerRef();

	private class InnerRef {
		public String uri;
	}
	
	public RestReference(String uri) {
		this.innerRef.uri = uri;
	}
	
	public InnerRef getRef() {
		return innerRef;
	}
}

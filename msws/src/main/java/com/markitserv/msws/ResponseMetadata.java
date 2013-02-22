package com.markitserv.msws;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.msws.util.CustomTimeStampSerializer;

public class ResponseMetadata {
	
	private String requestId;
	private DateTime sessionExpires;

	@JsonSerialize(using = CustomTimeStampSerializer.class)
	public DateTime getSessionExpires() {
		return sessionExpires;
	}

	public void setSessionExpires(DateTime sessionExpires) {
		this.sessionExpires = sessionExpires;
	}

	@JsonInclude(Include.NON_NULL)
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}

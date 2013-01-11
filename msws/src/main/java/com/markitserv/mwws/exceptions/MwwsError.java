package com.markitserv.mwws.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MwwsError {
	private String errorCode;
	private String errorMessage;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value = "Code")
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value = "Message")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

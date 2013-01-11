package com.markitserv.mwws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionResult extends GenericResult {

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

	public ExceptionResult(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}

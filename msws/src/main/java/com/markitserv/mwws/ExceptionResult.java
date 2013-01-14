package com.markitserv.mwws;

import java.util.List;
import java.util.Stack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markitserv.mwws.exceptions.MultipleErrorsException;
import com.markitserv.mwws.exceptions.MwwsException;

@JsonPropertyOrder(alphabetic=true)
public class ExceptionResult extends GenericResult {

	private MwwsException exception;
	private Stack<MwwsError> errors = new Stack<MwwsError>();

	private class MwwsError {

		private String errorCode;
		private String errorMessage;

		@JsonInclude(Include.NON_NULL)
		@JsonProperty(value = "Code")
		public String getErrorCode() {
			return errorCode;
		}

		@JsonInclude(Include.NON_NULL)
		@JsonProperty(value = "Message")
		public String getErrorMessage() {
			return errorMessage;
		}

		public MwwsError(String errorCode, String errorMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}
	}

	public ExceptionResult(MwwsException exception) {

		super();
		this.exception = exception;

		if (exception instanceof MultipleErrorsException) {
			MultipleErrorsException mException = (MultipleErrorsException) exception;
			for(String message : mException.getMessages()) {
				errors.push(new MwwsError(exception.getErrorCode(), message));
			}
		} else {
			errors.push(new MwwsError(exception.getErrorCode(), exception
					.getMessage()));
		}
	}

	@JsonProperty(value = "Errors")
	public List<MwwsError> errors() {
		return errors;
	}
}
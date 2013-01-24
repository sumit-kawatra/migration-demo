package com.markitserv.msws;

import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markitserv.msws.exceptions.MultipleErrorsException;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;

@JsonPropertyOrder(alphabetic = true)
public class ExceptionResult extends GenericResult {

	Logger log = LoggerFactory.getLogger(ExceptionResult.class);
	private Stack<MswsError> errors = new Stack<MswsError>();

	public static final String ERRORMESSAGE_GENERIC = "Some error occured. Please contact support team with the requestId";
	public static final String ERRORCODE_GENERIC = "Generic";
	public static final String EXCEPTION_MAIN = "UnknownException";

	public class MswsError {

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

		public MswsError(String errorCode, String errorMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
		}
	}

	public ExceptionResult(MswsException exception) {
		if (exception instanceof MultipleErrorsException) {
			MultipleErrorsException mException = (MultipleErrorsException) exception;
			for (String message : mException.getMessages()) {
				errors.push(new MswsError(exception.getErrorCode(), message));
			}
		} else if (exception instanceof ProgrammaticException) {
			errors.push(new MswsError(ERRORCODE_GENERIC, ERRORMESSAGE_GENERIC));
		} else {
			errors.push(new MswsError(exception.getErrorCode(), exception
					.getMessage()));
		}
	}

	@JsonProperty(value = "Errors")
	public List<MswsError> getErrors() {
		return errors;
	}

	// NOTE this will be handled by the dispatcher instead.
	/*
	 * private void setLogger(Exception exception) { StringBuilder str = new
	 * StringBuilder(); if(exception instanceof MwwsException) { MwwsException
	 * mwwsExc = (MwwsException)exception;
	 * str.append(CommonConstants.ERRORCODE).append(mwwsExc.getErrorCode()); }
	 * else {
	 * str.append(CommonConstants.ERRORCODE).append(CommonConstants.EXCEPTION_MAIN
	 * ); } str.append(CommonConstants.SCOLON);
	 * str.append(CommonConstants.ERRORMESSAGE) .append(exception.getMessage());
	 * log.info(str.toString()); }
	 */
}
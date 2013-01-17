package com.markitserv.mwws;

import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markitserv.mwws.exceptions.MultipleErrorsException;
import com.markitserv.mwws.exceptions.MwwsException;
import com.markitserv.mwws.exceptions.ProgrammaticException;

@JsonPropertyOrder(alphabetic = true)
public class ExceptionResult extends GenericResult {

	Logger log = LoggerFactory.getLogger(ExceptionResult.class);
	private Stack<MwwsError> errors = new Stack<MwwsError>();

	public static final String ERRORMESSAGE_GENERIC = "Some error occured. Please contact support team with the requestId";
	public static final String ERRORCODE_GENERIC = "Generic";
	public static final String EXCEPTION_MAIN = "UnknownException";

	public class MwwsError {

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
		if (exception instanceof MultipleErrorsException) {
			MultipleErrorsException mException = (MultipleErrorsException) exception;
			for (String message : mException.getMessages()) {
				errors.push(new MwwsError(exception.getErrorCode(), message));
			}
		} else if (exception instanceof ProgrammaticException) {
			errors.push(new MwwsError(ERRORCODE_GENERIC, ERRORMESSAGE_GENERIC));
		} else { // if exception type of Mws exception
			errors.push(new MwwsError(exception.getErrorCode(), exception
					.getMessage()));
		}
	}

	@JsonProperty(value = "Errors")
	public List<MwwsError> getErrors() {
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
package com.markitserv.msws.exceptions;

/**
 * Use when the client provides a value that's invalid but is not appropriate to 
 * check by a Validation.
 * @author roy.truelove
 *
 */
public class InvalidActionParamValueException extends MswsException {

	public InvalidActionParamValueException() {
		super();
	}

	public InvalidActionParamValueException(String format, Object... args) {
		super(format, args);
	}

	public InvalidActionParamValueException(String format, Throwable cause, Object... args) {
		super(format, cause, args);
	}

	public InvalidActionParamValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidActionParamValueException(String message) {
		super(message);
	}

	public InvalidActionParamValueException(Throwable cause) {
		super(cause);
	}
}

package com.markitserv.msws.exceptions;

/**
 * Thrown when an assertion is not met.  Should only be used by the MwwsAssert class.
 * @author roy.truelove
 *
 */
public class AssertionException extends ProgrammaticException {

	public AssertionException() {
		super();
	}

	public AssertionException(String format, Object... args) {
		super(format, args);
	}

	public AssertionException(String format, Throwable cause, Object... args) {
		super(format, cause, args);
	}

	public AssertionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertionException(String message) {
		super(message);
	}

	public AssertionException(Throwable cause) {
		super(cause);
	}
}

package com.markitserv.mwws.exceptions;

/**
 * Used for unexpected internal states that are the cause of a programming error.
 * Not expected to propegate to the end user, but should be used by the development
 * team for debugging.  If the error is *not* one that the user can fix, it should throw
 * this exception or a subclass
 * @author roy.truelove
 *
 */
public class ProgrammaticException extends MwwsException {

	public ProgrammaticException() {
		super();
	}

	public ProgrammaticException(String format, Object... args) {
		super(format, args);
	}

	public ProgrammaticException(String format, Throwable cause, Object... args) {
		super(format, cause, args);
	}

	public ProgrammaticException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProgrammaticException(String message) {
		super(message);
	}

	public ProgrammaticException(Throwable cause) {
		super(cause);
	}
}

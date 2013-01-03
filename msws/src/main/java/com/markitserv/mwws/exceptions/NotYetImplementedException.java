package com.markitserv.mwws.exceptions;

public class NotYetImplementedException extends ProgrammaticException {

	private static final long serialVersionUID = 1L;

	private NotYetImplementedException() {
		super();
	}

	private NotYetImplementedException(String format, Object... args) {
		super(format, args);
	}

	private NotYetImplementedException(String format, Throwable cause,
			Object... args) {
		super(format, cause, args);
	}

	private NotYetImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	private NotYetImplementedException(String message) {
		super(message);
	}

	private NotYetImplementedException(Throwable cause) {
		super(cause);
	}

	public static NotYetImplementedException standardException() {
		return new NotYetImplementedException(
				"You are trying to use functionality that is not yet implemented.");
	}
}

package com.markitserv.mwws.exceptions;

public class UnknownActionException extends MwwsException {

	public UnknownActionException() {
		super();
	}

	public UnknownActionException(String format, Object... args) {
		super(format, args);
	}

	public UnknownActionException(String format, Throwable cause,
			Object... args) {
		super(format, cause, args);
	}

	public UnknownActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownActionException(String message) {
		super(message);
	}

	public UnknownActionException(Throwable cause) {
		super(cause);
	}

	public static UnknownActionException standardException(String actionName) {
		throw new UnknownActionException(
				"Cannot find action with the name '%s'", actionName);
	}
}

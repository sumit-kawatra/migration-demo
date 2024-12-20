package com.markitserv.msws.exceptions;

public class UnknownActionException extends MswsException {

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
		return new UnknownActionException(
				"Cannot find action with the name '%s'", actionName);
	}
}

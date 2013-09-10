package com.markitserv.msws.exceptions;

public class MswsAccessDeniedException extends MswsException {

	public MswsAccessDeniedException() {
		super();
	}

	public MswsAccessDeniedException(String format, Object... args) {
		super(format, args);
	}

	public MswsAccessDeniedException(String format, Throwable cause,
			Object... args) {
		super(format, cause, args);
	}

	public MswsAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MswsAccessDeniedException(String message) {
		super(message);
	}

	public MswsAccessDeniedException(Throwable cause) {
		super(cause);
	}

	public static MswsAccessDeniedException standardException(String user,
			String action) {
		return new MswsAccessDeniedException(
				"User '%s' does not have the permissions to perform action '%s'",
				user, action);
	}
}

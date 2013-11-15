package com.markitserv.msws.exceptions;

import com.markitserv.msws.internal.exceptions.MswsException;

@Deprecated // Use Spring's AccessDeniedException
public class MswsAccessDeniedException extends MswsException {

	private static final long serialVersionUID = 1L;

	@Deprecated
	public MswsAccessDeniedException() {
		super();
	}

	@Deprecated
	public MswsAccessDeniedException(String format, Object... args) {
		super(format, args);
	}

	@Deprecated
	public MswsAccessDeniedException(String format, Throwable cause,
			Object... args) {
		super(format, cause, args);
	}

	@Deprecated
	public MswsAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	@Deprecated
	public MswsAccessDeniedException(String message) {
		super(message);
	}

	@Deprecated
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

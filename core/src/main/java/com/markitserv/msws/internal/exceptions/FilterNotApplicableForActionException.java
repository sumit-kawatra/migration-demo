package com.markitserv.msws.internal.exceptions;


public class FilterNotApplicableForActionException extends MswsException {

	public FilterNotApplicableForActionException() {
		super();
	}

	public FilterNotApplicableForActionException(String format, Object... args) {
		super(format, args);
	}

	public FilterNotApplicableForActionException(String format,
			Throwable cause, Object... args) {
		super(format, cause, args);
	}

	public FilterNotApplicableForActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilterNotApplicableForActionException(String message) {
		super(message);
	}

	public FilterNotApplicableForActionException(Throwable cause) {
		super(cause);
	}

	public static FilterNotApplicableForActionException standardException(
			String filterName, String actionName) {
		return new FilterNotApplicableForActionException(
				"Filter '%s' is not applicable for Action '%s'.", filterName,
				actionName);
	}
}

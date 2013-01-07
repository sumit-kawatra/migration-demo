package com.markitserv.mwws.exceptions;


public class ActionParamMissingException extends MwwsException {

	public ActionParamMissingException() {
		super();
	}

	public ActionParamMissingException(String format, Object... args) {
		super(format, args);
	}

	public ActionParamMissingException(String format, Throwable cause, Object... args) {
		super(format, cause, args);
	}

	public ActionParamMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActionParamMissingException(String message) {
		super(message);
	}

	public ActionParamMissingException(Throwable cause) {
		super(cause);
	}

	public static ActionParamMissingException standardException() {
		return new ActionParamMissingException(
				"Required 'Action' parameter is missing from request");
	}
}

package com.markitserv.msws.exceptions;

public class LongPollingInterruptedException extends MswsException {

	private static final long serialVersionUID = 1L;

	private LongPollingInterruptedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public static LongPollingInterruptedException standardException() {
		return new LongPollingInterruptedException(
				"Long polling action was interrupted.  Please try your request again");
	}
}

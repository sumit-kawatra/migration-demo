package com.markitserv.msws.internal.exceptions;

/**
 * Thrown when the client is polling at the poller times out.
 * 
 * @author roy.truelove
 * 
 */
public class PollingTimeoutException extends MswsException {

	private PollingTimeoutException(String message) {
		super(message);
	}

	@SuppressWarnings("unused")
	public static PollingTimeoutException standardException() {
		return new PollingTimeoutException(
				"Polling connection has timed out.  Please reconnect");
	}

	private static final long serialVersionUID = 1L;

}

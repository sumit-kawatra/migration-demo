package com.markitserv.rest;

public class RestRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RestRuntimeException() {
		super();
	}

	public RestRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestRuntimeException(String message) {
		super(message);
	}

	public RestRuntimeException(Throwable cause) {
		super(cause);
	}
}

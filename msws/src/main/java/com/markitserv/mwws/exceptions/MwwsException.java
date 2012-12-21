package com.markitserv.mwws.exceptions;

public class MwwsException extends RuntimeException {

	public MwwsException() {
	}

	public MwwsException(String message) {
		super(message);
	}

	public MwwsException(Throwable cause) {
		super(cause);
	}

	public MwwsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MwwsException(String format, Object... args){
		super(String.format(format, args));
	}
	
	public MwwsException(String format, Throwable cause, Object... args){
		super(String.format(format, args), cause);
	}
}


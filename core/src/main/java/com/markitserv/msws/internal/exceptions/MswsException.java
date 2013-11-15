package com.markitserv.msws.internal.exceptions;

/**
 * Parent classes for all MarkitServ Web Services exceptions
 * 
 * @author roy.truelove
 * 
 */
public class MswsException extends RuntimeException {

	private String errorMessage;
	
	public MswsException() {
	}

	public MswsException(String message) {
		super(message);
		this.errorMessage = message;
	}

	public MswsException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return getClass().getSimpleName();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public MswsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MswsException(String format, Object... args) {
		super(String.format(format, args));
	}

	public MswsException(String format, Throwable cause, Object... args) {
		super(String.format(format, args), cause);
	}

	/**
	 * When applicable, each subclass should implement this function. This
	 * *particular* method should never be called (hence why it's private), it's
	 * intended only for subclasses and is here for documentation purposes only.
	 * 
	 * @return
	 */
	private static MswsException standardException() {
		return new MswsException("Unknown Web Services Exception");
	}
}

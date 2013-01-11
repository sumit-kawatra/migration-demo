package com.markitserv.mwws.exceptions;

/**
 * Parent classes for all
 * 
 * @author roy.truelove
 * 
 */
public class MwwsException extends RuntimeException {

	
	private String errorCode;
	private String errorMessage;
	
	
	public MwwsException() {
	}

	public MwwsException(String message) {
		super(message);
		this.errorCode = getClass().getSimpleName();
		this.errorMessage = message;
	}

	public MwwsException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public MwwsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MwwsException(String format, Object... args) {
		super(String.format(format, args));
	}

	public MwwsException(String format, Throwable cause, Object... args) {
		super(String.format(format, args), cause);
	}

	/**
	 * When applicable, each subclass should implement this function. This
	 * *particular* method should never be called (hence why it's private), it's
	 * intended only for subclasses and is here for documentation purposes only.
	 * 
	 * @return
	 */
	private static MwwsException standardException() {
		return new MwwsException("Unknown Web Services Exception");
	}
}

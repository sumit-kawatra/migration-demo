package com.markitserv.msws.internal.exceptions;

/**
 * Should be thrown if the author of an action did not fully implement all
 * descriptions, default values, etc. etc
 * 
 * @author roy.truelove
 * 
 */
public class IncompleteActionDefinitionException extends ProgrammaticException {

	private static final long serialVersionUID = 1L;

	public IncompleteActionDefinitionException() {
		super();
	}

	public IncompleteActionDefinitionException(String format, Object... args) {
		super(format, args);
	}

	public IncompleteActionDefinitionException(String format, Throwable cause,
			Object... args) {
		super(format, cause, args);
	}

	public IncompleteActionDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompleteActionDefinitionException(String message) {
		super(message);
	}

	public IncompleteActionDefinitionException(Throwable cause) {
		super(cause);
	}
	
}

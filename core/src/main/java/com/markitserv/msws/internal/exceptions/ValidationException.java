package com.markitserv.msws.internal.exceptions;

import java.util.List;

public class ValidationException extends MultipleErrorsException {

	private static final long serialVersionUID = 1L;

	private List<String> validationMsgs;

	public ValidationException() {
		super();
	}

	public ValidationException(String msg, List<String> allMsgs) {
		super(msg, allMsgs);
		validationMsgs = allMsgs;
	}

	public List<String> getValidationMsgs() {
		return validationMsgs;
	}

}

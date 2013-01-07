package com.markitserv.mwws.exceptions;

import java.util.List;

public class ValidationException extends MwwsException {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> validationMsgs;
	
	public ValidationException(String msg, List<String> allMsgs) {
		super(msg);
		validationMsgs = allMsgs;
	}

	public List<String> getValidationMsgs() {
		return validationMsgs;
	}
}

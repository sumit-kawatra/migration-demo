package com.markitserv.mwws.exceptions;

import java.util.List;

public class ValidationException extends MultipleErrorsException {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> validationMsgs;
	
	public ValidationException(String msg, List<String> allMsgs) {
		super(msg, allMsgs);
		//TODO test this iwth super(msg); and handle that exception ?
		validationMsgs = allMsgs;
	}

	public List<String> getValidationMsgs() {
		return validationMsgs;
	}

}

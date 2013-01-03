package com.markitserv.mwws.validation;

public class ValidationResponse {
	
	private boolean isValid = false;
	private String msgTemplate = null;
	
	public static ValidationResponse createInvalidResponse(String msg) {
		return new ValidationResponse(false, msg);
	}
	
	public static ValidationResponse createValidResponse() {
		return new ValidationResponse(true, null);
	}
	
	private ValidationResponse(boolean isValid, String msgTemplate) {
		super();
		this.isValid = isValid;
		this.msgTemplate = msgTemplate;
	}

	public boolean isValid() {
		return isValid;
	}
	public String getMessage() {
		return msgTemplate;
	}
}

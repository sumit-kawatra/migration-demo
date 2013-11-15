package com.markitserv.msws.validation;

/**
 * Encapsulates the response from a validation and conversion method
 * 
 * @author roy.truelove
 * 
 */
public class ValidationResponse {

	private boolean isValid = false;
	private String msg = null;
	private Object convertedObj = null;

	public static ValidationResponse createInvalidResponse(String msg) {
		return new ValidationResponse(false, msg, null);
	}

	public static ValidationResponse createValidConvertedResponse(
			Object convertedVal) {
		return new ValidationResponse(true, null, convertedVal);
	}

	public static ValidationResponse createInvalidConvertedResponse(
			Object convertedVal, String msg) {
		return new ValidationResponse(false, null, convertedVal);
	}

	private ValidationResponse(boolean isValid, String msgTemplate,
			Object convertedObject) {
		super();
		this.isValid = isValid;
		this.msg = msgTemplate;
		this.convertedObj = convertedObject;
	}

	public boolean isValid() {
		return isValid;
	}

	public String getMessage() {
		return msg;
	}

	public Object getConvertedObj() {
		return convertedObj;
	}

	public void setConvertedObj(Object convertedObj) {
		this.convertedObj = convertedObj;
	}
}

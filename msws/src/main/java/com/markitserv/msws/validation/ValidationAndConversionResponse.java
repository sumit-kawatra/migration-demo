package com.markitserv.msws.validation;

/**
 * Encapsulates the response from a validation and converstion method
 * @author roy.truelove
 *
 */
public class ValidationAndConversionResponse {
	
	private boolean isValid = false;
	private String msg = null;
	private Object convertedObj = null;
	
	public static ValidationAndConversionResponse createInvalidResponse(String msg) {
		return new ValidationAndConversionResponse(false, msg, null);
	}
	
	public static ValidationAndConversionResponse createValidResponse() {
		return new ValidationAndConversionResponse(true, null, null);
	}
	
	public static ValidationAndConversionResponse createValidConvertedResponse(Object convertedVal) {
		return new ValidationAndConversionResponse(true, null, convertedVal);
	}
	
	public static ValidationAndConversionResponse createInvalidConvertedResponse(Object convertedVal, String msg) {
		return new ValidationAndConversionResponse(false, null, convertedVal);
	}
	
	private ValidationAndConversionResponse(boolean isValid, String msgTemplate, Object convertedObject) {
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

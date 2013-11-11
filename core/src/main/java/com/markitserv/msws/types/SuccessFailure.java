package com.markitserv.msws.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * SuccessFailure Type
 * 
 * @author swati.choudhari
 * 
 */
public class SuccessFailure {

	private boolean success = true;
	private String msg;
	
	@JsonInclude(Include.NON_NULL)
	public String getMessage() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public SuccessFailure(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


}

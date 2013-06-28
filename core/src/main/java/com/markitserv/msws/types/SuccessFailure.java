package com.markitserv.msws.types;

/**
 * SuccessFailure Type 
 * @author swati.choudhari
 *
 */
public class SuccessFailure {
 private boolean success = true;

public boolean isSuccess() {
	return success;
}

public void setSuccess(boolean success) {
	this.success = success;
}

public SuccessFailure(boolean success) {
	super();
	this.success = success;
}
 
}

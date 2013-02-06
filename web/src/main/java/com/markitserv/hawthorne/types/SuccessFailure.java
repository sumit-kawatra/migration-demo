package com.markitserv.hawthorne.types;

import com.markitserv.msws.Type;

/**
 * SuccessFailure Type 
 * @author swati.choudhari
 *
 */
public class SuccessFailure extends Type {
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

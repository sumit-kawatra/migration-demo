package com.markitserv.msws.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.markitserv.msws.internal.Constants;

public class PaginatedActionResponseMetaData extends ActionResponseMetadata {

	// using Integer and not int so that they can be null
	private int totalRecords = Constants.INTEGER_NOT_SET;
	private int approxTotalRecords = Constants.INTEGER_NOT_SET;

	@JsonInclude(Include.NON_DEFAULT)
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	@JsonInclude(Include.NON_DEFAULT)
	public int getApproxTotalRecords() {
		return approxTotalRecords;
	}

	public void setApproxTotalRecords(int approxTotalRecords) {
		this.approxTotalRecords = approxTotalRecords;
	}
} 
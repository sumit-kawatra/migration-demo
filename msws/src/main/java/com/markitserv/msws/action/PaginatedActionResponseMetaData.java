package com.markitserv.msws.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.markitserv.msws.internal.Constants;

public class PaginatedActionResponseMetaData extends ActionResponseMetadata {

	private int totalRecords = Constants.INTEGER_NOT_SET;
	private int approxTotalRecords = Constants.INTEGER_NOT_SET;
	private int totalFilteredRecords = Constants.INTEGER_NOT_SET;
	private int requestRecords = Constants.INTEGER_NOT_SET;

	public int getRequestRecords() {
		return requestRecords;
	}

	public void setRequestRecords(int requestRecords) {
		this.requestRecords = requestRecords;
	}

	@JsonInclude(Include.NON_DEFAULT)
	public int getTotalFilteredRecords() {
		return totalFilteredRecords;
	}

	public void setTotalFilteredRecords(int totalFilteredRecords) {
		this.totalFilteredRecords = totalFilteredRecords;
	}

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
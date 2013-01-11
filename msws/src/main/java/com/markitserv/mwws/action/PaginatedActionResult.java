package com.markitserv.mwws.action;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.markitserv.mwws.Type;
import com.markitserv.mwws.Util.Constants;

public class PaginatedActionResult extends ActionResult {
	
	// using Integer and not int so that they can be null
	private int totalRecords = Constants.INTEGER_NOT_SET;
	private int approxTotalRecords = Constants.INTEGER_NOT_SET;
	
	public PaginatedActionResult(List<? extends Type> list) {
		super(list);
	}
	
	public PaginatedActionResult() {
		super();
	}

	@JsonIgnore
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	@JsonIgnore
	public int getApproxTotalRecords() {
		return approxTotalRecords;
	}

	public void setApproxTotalRecords(int approxTotalRecords) {
		this.approxTotalRecords = approxTotalRecords;
	}
	
} 
package com.markitserv.msws.action;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.markitserv.msws.internal.Constants;

public class PaginatedActionResponseMetaData extends ActionResponseMetadata {

	private int totalRecords = Constants.INTEGER_NOT_SET;
	private int totalFilteredRecords = Constants.INTEGER_NOT_SET;
	private int requestRecords = Constants.INTEGER_NOT_SET;

	/**
	 * The number of records actually returned by this request, after pagination
	 */
	@JsonInclude(Include.NON_DEFAULT)
	public int getRequestRecords() {
		return requestRecords;
	}

	public void setRequestRecords(int requestRecords) {
		this.requestRecords = requestRecords;
	}

	/**
	 * The number of records after filtering (i.e. the total number of records after
	 * filtering has been applied - not just the number of records being
	 * returned in this result set)
	 */
	@JsonInclude(Include.NON_DEFAULT)
	public int getTotalFilteredRecords() {
		return totalFilteredRecords;
	}

	public void setTotalFilteredRecords(int totalFilteredRecords) {
		this.totalFilteredRecords = totalFilteredRecords;
	}

	/**
	 * Total number of records, before filtering and pagination (if known)
	 * @return
	 */
	@JsonInclude(Include.NON_DEFAULT)
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
} 
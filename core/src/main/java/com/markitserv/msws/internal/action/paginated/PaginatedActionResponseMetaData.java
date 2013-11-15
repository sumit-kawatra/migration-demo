package com.markitserv.msws.internal.action.paginated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.markitserv.msws.internal.Constants;
import com.markitserv.msws.internal.action.ActionResponseMetadata;

public class PaginatedActionResponseMetaData extends ActionResponseMetadata {

	private int totalRecords = Constants.INTEGER_NOT_SET;
	private int requestRecords = Constants.INTEGER_NOT_SET;

	private boolean totalIsEstimated = false;

	/**
	 * The number of records actually returned by this request, after pagination
	 */
	@JsonInclude(Include.NON_DEFAULT)
	public int getRequestRecords() {
		return requestRecords;
	}

	/**
	 * Total number of records, before filtering and pagination
	 * 
	 * @return
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	// When the total is estimated, this will return true.
	@JsonInclude(Include.NON_DEFAULT)
	public boolean isTotalIsEstimated() {
		return totalIsEstimated;
	}

	public void setRequestRecords(int requestRecords) {
		this.requestRecords = requestRecords;
	}

	public void setTotalIsEstimated(boolean totalIsEstimated) {
		this.totalIsEstimated = totalIsEstimated;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
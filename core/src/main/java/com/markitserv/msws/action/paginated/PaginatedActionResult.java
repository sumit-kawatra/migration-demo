package com.markitserv.msws.action.paginated;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.markitserv.msws.action.resp.ActionResult;
import com.markitserv.msws.internal.action.ResponseMetadata;
import com.markitserv.msws.internal.action.paginated.PaginatedActionResponseMetaData;

public class PaginatedActionResult extends ActionResult {

	protected PaginatedActionResponseMetaData metaData;

	/**
	 * 
	 * @param list
	 *            - The paginated list of items
	 * @param totalRecordCount
	 *            - count of *all* records, before filtering and paginating
	 * @param totalIsEstimated
	 *            - true if the total is estimated.
	 */
	public PaginatedActionResult(List<? extends Object> list,
			int totalRecordCount, boolean totalIsEstimated) {

		super(list);

		this.metaData = new PaginatedActionResponseMetaData();
		this.metaData.setTotalRecords(totalRecordCount);
		this.metaData.setTotalIsEstimated(totalIsEstimated);
	}

	public PaginatedActionResult(List<? extends Object> list,
			int totalRecordCount) {

		this(list, totalRecordCount, false);

	}

	@JsonIgnore
	public PaginatedActionResponseMetaData getPaginatedMetaData() {
		return metaData;
	}

	@Override
	public ResponseMetadata getMetaData() {
		return metaData;
	}
}
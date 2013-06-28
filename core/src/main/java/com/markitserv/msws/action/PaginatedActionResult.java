package com.markitserv.msws.action;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.markitserv.msws.ResponseMetadata;
import com.markitserv.msws.internal.Constants;

public class PaginatedActionResult extends ActionResult {
	
	protected PaginatedActionResponseMetaData metaData = new PaginatedActionResponseMetaData();
	
	public PaginatedActionResult(List<? extends Object> list) {
		super(list);
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
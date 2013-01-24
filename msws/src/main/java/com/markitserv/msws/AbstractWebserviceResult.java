package com.markitserv.msws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public abstract class AbstractWebserviceResult {

	protected ResponseMetadata metaData = new ResponseMetadata();
	
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(value = "responseMetadata")
	public ResponseMetadata getMetaData() {
		return metaData;
	}

	public void setMetaData(ResponseMetadata metaData) {
		this.metaData = metaData;
	}
}

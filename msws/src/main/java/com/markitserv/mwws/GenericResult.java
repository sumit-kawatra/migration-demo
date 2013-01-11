package com.markitserv.mwws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class GenericResult {

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

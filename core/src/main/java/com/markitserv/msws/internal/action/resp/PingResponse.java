package com.markitserv.msws.internal.action.resp;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.msws.action.resp.SuccessFailure;
import com.markitserv.msws.internal.util.JsonTimeStampSerializer;

/**
 * @author kiran.gogula
 * 
 */
public class PingResponse extends SuccessFailure {

	public PingResponse(boolean success) {
		super(success);
	}

	@JsonSerialize(using = JsonTimeStampSerializer.class)
	private DateTime currentTimpstamp;

	public DateTime getCurrentTimpstamp() {
		return currentTimpstamp;
	}

	public void setCurrentTimpstamp(DateTime currentTimpstamp) {
		this.currentTimpstamp = currentTimpstamp;
	}
}


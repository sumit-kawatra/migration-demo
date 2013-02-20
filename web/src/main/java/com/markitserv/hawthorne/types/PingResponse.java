/**
 * 
 */
package com.markitserv.hawthorne.types;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.msws.util.CustomTimeStampSerializer;

/**
 * @author kiran.gogula
 *
 */
public class PingResponse extends SuccessFailure{

	public PingResponse(boolean success) {
		super(success);
	}
	
	@JsonSerialize(using = CustomTimeStampSerializer.class)
	private DateTime currentTimpstamp;

	public DateTime getCurrentTimpstamp() {
		return currentTimpstamp;
	}

	public void setCurrentTimpstamp(DateTime currentTimpstamp) {
		this.currentTimpstamp = currentTimpstamp;
	}
	
	
	
	

}

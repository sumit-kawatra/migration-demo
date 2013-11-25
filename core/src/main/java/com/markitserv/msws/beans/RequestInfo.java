package com.markitserv.msws.beans;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class RequestInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private DateTime timestamp;
	private String shortId;

	/**
	 * For the purpose of logging / auditing a request we don't, in practice,
	 * need the whole id.
	 * 
	 * @return
	 */
	public String getShortId() {
		
		if (shortId == null) {
			shortId = StringUtils.left(id, 8);
		}
		return shortId;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getRequestId() {
		return id;
	}

	public void setRequestId(String requestId) {
		this.id = requestId;
	}

}

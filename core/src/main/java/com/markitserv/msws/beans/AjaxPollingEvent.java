package com.markitserv.msws.beans;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.markitserv.msws.internal.util.JsonDateSerializer;
import com.markitserv.msws.internal.util.JsonTimeStampSerializer;

/**
 * All long polling events will be wrapped in this type
 * 
 * @author roy.truelove
 * 
 * @param <T>
 */
public class AjaxPollingEvent<T> {

	private String eventType;
	private T payload;
	private DateTime timestamp;
	private String id;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@JsonInclude(Include.NON_NULL)
	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	@JsonSerialize(using = JsonTimeStampSerializer.class)
	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "AjaxPollingEvent [eventType=" + eventType + ", payload="
				+ payload + ", timestamp=" + timestamp + "]";
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}

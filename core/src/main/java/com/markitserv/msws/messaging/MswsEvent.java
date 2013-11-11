package com.markitserv.msws.messaging;

import java.util.HashMap;
import java.util.Map;

public class MswsEvent {

	private Map<String, Object> metaData = new HashMap<String, Object>();
	private Object payload;
	
	public MswsEvent(String eventName) {
		super();
		this.eventName = eventName;
	}

	private String eventName;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public final void putMetadata(String key, Object val) {
		metaData.put(key, val);
	}

	public Object getMetadata(String key, Object val) {
		return metaData.get(key);
	}

	public final Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
}

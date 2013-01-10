package com.markitserv.hawthorne.types;

import com.markitserv.mwws.Type;

public class TradingRequestStatus extends Type {
	
	public TradingRequestStatus() {
		super();
	}
	
	public TradingRequestStatus(long id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	
	private long id;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}

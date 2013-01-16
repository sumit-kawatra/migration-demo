package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
	
	@Override
	public int hashCode() {
		   return HashCodeBuilder.reflectionHashCode(id);
	 }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		TradingRequestStatus tradeRequestStatus = (TradingRequestStatus)obj;
		return  new EqualsBuilder().appendSuper(super.equals(obj))
				.append(id, tradeRequestStatus.id).isEquals();
	}

	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}

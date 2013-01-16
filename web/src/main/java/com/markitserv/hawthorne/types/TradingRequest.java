package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.mwws.Type;

public class TradingRequest extends Type {

	private long id;
	private TradingRequestStatus requestStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TradingRequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(TradingRequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
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
		TradingRequest tr = (TradingRequest) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(id, tr.id).isEquals();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.Type;

public class LegalEntity extends Type {

	private int id;
	private String name;
	private String bic;
	private boolean isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
		LegalEntity legalEntity = (LegalEntity)obj;
		return  new EqualsBuilder().appendSuper(super.equals(obj))
				.append(id, legalEntity.id).isEquals();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
		ToStringStyle.MULTI_LINE_STYLE);
	}

}
package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.mwws.Type;

public class LegalEntity extends Type {

	private int id;
	private String name;
	private String bic;

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

	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 45).append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		LegalEntity rhs = (LegalEntity) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(id, rhs.id).isEquals();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
		ToStringStyle.MULTI_LINE_STYLE);
	}
}
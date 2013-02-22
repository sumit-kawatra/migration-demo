/**
 * 
 */
package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.Type;

/**
 * @author kiran.gogula
 * 
 */
public class Book extends Type {

	private int id;

	private String name;

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
		Book book = (Book) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(id, book.id)
				.isEquals();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}

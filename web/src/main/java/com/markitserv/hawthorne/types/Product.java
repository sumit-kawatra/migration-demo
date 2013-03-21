package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.markitserv.msws.Type;

/**
 * Product Type
 * 
 * @author swati.choudhari
 * 
 */
public class Product extends Type {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int productId) {
		this.id = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String productName) {
		this.name = productName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Product(int productId, String productName) {
		super();
		this.id = productId;
		this.name = productName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}

}

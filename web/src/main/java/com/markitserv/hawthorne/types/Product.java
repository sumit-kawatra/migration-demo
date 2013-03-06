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
	private int productId;
	private String productName;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Product(int productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(productId, productName);
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equal(this, obj);
	}

}

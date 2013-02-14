package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.Type;

/**
 * Product Type
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
		return ReflectionToStringBuilder.toString(this,
		ToStringStyle.MULTI_LINE_STYLE);
	}

	public Product(int productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}
	
	@Override
	public int hashCode() {
		   return HashCodeBuilder.reflectionHashCode(productId);
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
		Product product = (Product)obj;
		return  new EqualsBuilder().appendSuper(super.equals(obj))
				.append(productId, product.productId).isEquals();
	}
	
}

/**
 * 
 */
package com.markitserv.hawthorne.types;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.Type;

/**
 * @author kiran.gogula
 *
 */
public class InterestGroup extends Type{
	
	private String name;
	
	private String shortName;
	
	private boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	

}
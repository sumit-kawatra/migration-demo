/**
 * 
 */
package com.markitserv.hawthorne.types;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.markitserv.msws.Type;

/**
 * @author kiran.gogula
 * 
 */
public class SubGroup extends Type {

	private String name;

	private String shortName;

	private boolean active;
	
	private List<User> subGroupUser;

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
	

	public List<User> getSubGroupUser() {
		return subGroupUser;
	}

	public void setSubGroupUser(List<User> subGroupUser) {
		this.subGroupUser = subGroupUser;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}

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
public class User extends Type {
	
	private int  userId;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private int legalEntityId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getLegalEntityId() {
		return legalEntityId;
	}

	public void setLegalEntityId(int legalEntityId) {
		this.legalEntityId = legalEntityId;
	}

	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
}
	
	

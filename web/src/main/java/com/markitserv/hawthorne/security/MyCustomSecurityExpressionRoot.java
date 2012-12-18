/**
 * 
 */
package com.markitserv.hawthorne.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author swati.choudhari
 *
 */

public class MyCustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
	
	private Object filterObject;
	private Object returnObject;
	private Object target;
	
	public Object getFilterObject() {
		return filterObject;
	}

	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
	public MyCustomSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}
	
	public boolean myCustomMethodCheck() {
		System.out.println("**Passing myCustomMethodCheck Successfully**");
		// complex logic will go here
		return true;
    }

	@Override
	public Object getThis() {
		// TODO Auto-generated method stub
		return this;
	}

	
}

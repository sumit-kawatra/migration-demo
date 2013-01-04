package com.markitserv.mwws.validation;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractValidation {
	
	abstract public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map);
	
	public ValidationResponse isValidInternal(Object target,
			Map<String, ? extends Object> map) {
		
		return isValid(target, map);
	}
	
	/**
	 * Sees if an object is null or empty
	 * @param target
	 * @return
	 */
	public boolean isProvided(Object target) {
		
		boolean isBlank = false;
		
		if (target == null) {
			isBlank = true;
		} else if (target instanceof String
				&& StringUtils.isBlank((String) target)) {
			isBlank = true;
		} else if (target instanceof Collection<?>
				&& ((Collection<?>) target).size() == 0) {
			isBlank = true;
		} else {
			isBlank = false;
		}
		return isBlank;
	}
}
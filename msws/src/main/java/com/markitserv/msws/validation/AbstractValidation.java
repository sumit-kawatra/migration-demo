package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractValidation {

	abstract public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> otherValues);

	/**
	 * Template method. This is the method that should be called when
	 * validating, but subclasses should override isValid
	 * 
	 * @param target
	 * @param otherValues
	 * @return
	 */
	public ValidationResponse isValidInternal(Object target,
			Map<String, ? extends Object> otherValues) {

		return isValid(target, otherValues);
	}

	/**
	 * Sees if an object is null or empty
	 * 
	 * @param target
	 * @return
	 */
	public boolean isProvided(Object target) {

		boolean isProvided = false;

		if (target == null) {
			isProvided = false;
		} else if (target instanceof String
				&& StringUtils.isBlank((String) target)) {
			isProvided = false;
		} else if (target instanceof Collection<?>
				&& ((Collection<?>) target).size() == 0) {
			isProvided = false;
		} else {
			isProvided = true;
		}
		return isProvided;
	}
}
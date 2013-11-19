package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractValidation {

	Logger log = LoggerFactory.getLogger(this.getClass());

	abstract protected ValidationResponse validate(Object target,
			Map<String, ? extends Object> otherValues);

	/**
	 * Template method. This is the method that should be called when
	 * validating, but subclasses should override validate
	 * 
	 * @param target
	 * @param otherValues
	 * @return
	 */
	public ValidationResponse validateInternal(Object target,
			Map<String, ? extends Object> otherValues) {

		return validate(target, otherValues);
	}

	/**
	 * Convience method. Sees if an object is null or empty. Mostly used by
	 * 'Required' validations
	 * 
	 * @param target
	 * @return
	 */
	protected boolean isProvided(Object target) {

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

	/**
	 * Describes this validation. Should be made abstract in future versions
	 * 
	 * @return
	 */
	public String getDescription() {
		log.warn("No description provided for validation class: "
				+ this.getClass().getCanonicalName());
		return "No description provided";
	}

	public ValidationResponse createInvalidResponse() {

		return ValidationResponse
				.createInvalidResponse("Does not meet the following critiera: "
						+ this.getDescription());

	}

}
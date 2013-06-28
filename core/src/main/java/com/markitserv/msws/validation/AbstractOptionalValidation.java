package com.markitserv.msws.validation;

import java.util.Map;

/**
 * Used for all validations that are not required. Will never be invalid if the
 * value is not provided.
 * 
 * @author roy.truelove
 * 
 */
public abstract class AbstractOptionalValidation extends AbstractValidation {

	@Override
	public ValidationAndConversionResponse validateInternal(Object target,
			Map<String, ? extends Object> map) {

		ValidationAndConversionResponse resp;

		if (!isProvided(target)) {
			resp = ValidationAndConversionResponse.createValidResponse();
		} else {
			resp = super.validateInternal(target, map);
		}

		return resp;
	}
}
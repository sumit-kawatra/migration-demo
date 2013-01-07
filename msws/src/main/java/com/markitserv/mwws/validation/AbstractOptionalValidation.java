package com.markitserv.mwws.validation;

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
	public ValidationResponse isValidInternal(Object target,
			Map<String, ? extends Object> map) {

		ValidationResponse resp;

		if (!isProvided(target)) {
			resp = ValidationResponse.createValidResponse();
		} else {
			resp = super.isValidInternal(target, map);
		}

		return resp;
	}
}

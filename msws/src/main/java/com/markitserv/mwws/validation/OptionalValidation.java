package com.markitserv.mwws.validation;

import java.util.Map;

public class OptionalValidation implements Validation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {
		// always valid.
		return ValidationResponse.createValidResponse();
	}
}

package com.markitserv.msws.validation;

import java.util.Map;

public class OptionalValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {
		// always valid.
		return ValidationResponse.createValidResponse();
	}
}

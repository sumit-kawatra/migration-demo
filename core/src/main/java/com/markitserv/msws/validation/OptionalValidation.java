package com.markitserv.msws.validation;

import java.util.Map;

public class OptionalValidation extends AbstractOptionalValidation {

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {
		// always valid.
		return ValidationAndConversionResponse.createValidResponse();
	}
}

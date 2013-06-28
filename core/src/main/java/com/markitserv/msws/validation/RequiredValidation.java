package com.markitserv.msws.validation;

import java.util.Map;

public class RequiredValidation extends AbstractValidation {

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {
		ValidationAndConversionResponse resp;

		if (!isProvided(target)) {
			resp = ValidationAndConversionResponse
					.createInvalidResponse("Required but not provided.");
		} else {
			resp = ValidationAndConversionResponse.createValidResponse();
		}
		return resp;
	}
}

package com.markitserv.msws.validation;

import java.util.Map;

public class RequiredValidation extends AbstractValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {
		ValidationResponse resp;

		if (!isProvided(target)) {
			resp = ValidationResponse
					.createInvalidResponse("Required but not provided.");
		} else {
			resp = ValidationResponse.createValidConvertedResponse(target);
		}
		return resp;
	}

	@Override
	public String getDescription() {
		return "Required";
	}
}

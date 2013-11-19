package com.markitserv.msws.validation;

import java.util.Map;

public class OptionalValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {
		// always valid.
		return ValidationResponse.createValidConvertedResponse(target);
	}

	@Override
	public String getDescription() {
		return "Is Optional";
	}
}

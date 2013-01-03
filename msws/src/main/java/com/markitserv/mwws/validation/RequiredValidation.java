package com.markitserv.mwws.validation;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class RequiredValidation implements Validation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {
		ValidationResponse resp;

		if (target == null) {
			resp = bad();
		} else if (target instanceof String
				&& StringUtils.isBlank((String) target)) {
			resp = bad();
		} else if (target instanceof Collection<?>
				&& ((Collection<?>) target).size() == 0) {
			resp = bad();
		} else {
			resp = ValidationResponse.createValidResponse();
		}
		return resp;
	}

	private ValidationResponse bad() {
		return ValidationResponse
				.createInvalidResponse("Required but not provided.");
	}
}

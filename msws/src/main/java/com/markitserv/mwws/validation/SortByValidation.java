package com.markitserv.mwws.validation;

import java.util.List;
import java.util.Map;

public class SortByValidation extends OneOfValidation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {
		ValidationResponse resp = super.isValid(target, map);
		if (!resp.isValid()) {
			return ValidationResponse
					.createInvalidResponse("Could not sort by value '"
							+ target.toString() + "'.  " + resp.getMessage());
		} else {
			return ValidationResponse.createValidResponse();
		}
	}

	private SortByValidation(String[] validValues) {
		super(validValues);
	}

	public SortByValidation(List<String> validValues) {
		super(validValues);
	}
}

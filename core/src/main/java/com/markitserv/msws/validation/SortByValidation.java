package com.markitserv.msws.validation;

import java.util.List;
import java.util.Map;

public class SortByValidation extends OneOfValidation {

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {
		ValidationAndConversionResponse resp = super.validate(target, map);
		if (!resp.isValid()) {
			return ValidationAndConversionResponse
					.createInvalidResponse("Could not sort by value '"
							+ target.toString() + "'.  " + resp.getMessage());
		} else {
			return ValidationAndConversionResponse.createValidResponse();
		}
	}

	public SortByValidation(String[] validValues) {
		super(validValues);
	}

	public SortByValidation(List<String> validValues) {
		super(validValues);
	}
}

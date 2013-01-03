package com.markitserv.mwws.validation;

import java.util.Map;

public class IntegerValidation implements Validation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		if (target == null) {
			return ValidationResponse.createValidResponse();
		} else if (target instanceof Integer) {
			return ValidationResponse.createValidResponse();
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			try {
				Integer.parseInt(targetStr);
			} catch (Exception e) {
				return bad();
			}
		}
		
		return ValidationResponse.createValidResponse();
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected Integer");
	}
}

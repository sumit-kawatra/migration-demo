package com.markitserv.msws.validation;

import java.util.Map;

public class FloatValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {
		
		Float convertedFloat = null;

		if (target instanceof Float) {
			return ValidationResponse.createValidConvertedResponse(target);
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			try {
				convertedFloat = Float.parseFloat(targetStr);
			} catch (Exception e) {
				return bad();
			}
		}
		
		return ValidationResponse.createValidConvertedResponse(convertedFloat);
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected Integer");
	}
}

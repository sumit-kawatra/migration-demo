package com.markitserv.msws.validation;

import java.util.Map;

public class IntegerValidationAndConversion extends AbstractConversionValidation {

	@Override
	public ValidationAndConversionResponse validateAndConvert(Object target,
			Map<String, ? extends Object> map) {
		
		Integer convertedInt = null;

		if (target instanceof Integer) {
			return ValidationAndConversionResponse.createValidConvertedResponse(target);
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			try {
				convertedInt = Integer.parseInt(targetStr);
			} catch (Exception e) {
				return bad();
			}
		}
		
		return ValidationAndConversionResponse.createValidConvertedResponse(convertedInt);
	}

	private ValidationAndConversionResponse bad() {
		return ValidationAndConversionResponse.createInvalidResponse("Expected Integer");
	}
}

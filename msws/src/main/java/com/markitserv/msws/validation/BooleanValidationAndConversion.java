package com.markitserv.msws.validation;

import java.util.Map;

public class BooleanValidationAndConversion extends AbstractConversionValidation {

	@Override
	public ValidationAndConversionResponse validateAndConvert(Object target,
			Map<String, ? extends Object> map) {

		if (target instanceof Boolean) {
			return ValidationAndConversionResponse.createValidConvertedResponse(target);
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			if("true".equalsIgnoreCase(targetStr) || "false".equalsIgnoreCase(targetStr)){
				return ValidationAndConversionResponse.createValidConvertedResponse(new Boolean(targetStr));
			}else{
				return bad();
			}
		}
	}

	private ValidationAndConversionResponse bad() {
		return ValidationAndConversionResponse.createInvalidResponse("Expected Boolean");
	}
}

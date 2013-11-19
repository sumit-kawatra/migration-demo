package com.markitserv.msws.validation;

import java.math.BigDecimal;
import java.util.Map;

public class BigDecimalValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {
		
		BigDecimal converted = null;

		if (target instanceof BigDecimal) {
			return ValidationResponse.createValidConvertedResponse(target);
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			try {
				converted = new BigDecimal(targetStr);
			} catch (Exception e) {
				return bad();
			}
		}
		
		return ValidationResponse.createValidConvertedResponse(converted);
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected BigDecimal");
	}

	@Override
	public String getDescription() {
		return "Is convertible to decimal value";
	}
	
	
}

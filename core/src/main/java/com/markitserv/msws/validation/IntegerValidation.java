package com.markitserv.msws.validation;

import java.util.Map;

public class IntegerValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {
		
		Integer convertedInt = null;

		if (target instanceof Integer) {
			return ValidationResponse.createValidConvertedResponse(target);
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
		
		return ValidationResponse.createValidConvertedResponse(convertedInt);
	}

	private ValidationResponse bad() {
		return createInvalidResponse();
	}

	@Override
	public String getDescription() {
		return "Is convertable to integer value";
	}
}

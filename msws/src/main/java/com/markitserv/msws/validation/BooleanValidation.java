package com.markitserv.msws.validation;

import java.util.Map;

public class BooleanValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		if (target instanceof Boolean) {
			return ValidationResponse.createValidResponse();
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			if("true".equalsIgnoreCase(targetStr) || "false".equalsIgnoreCase(targetStr)){
				return ValidationResponse.createValidResponse();
			}else{
				return bad();
			}
		}
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected Boolean");
	}
}

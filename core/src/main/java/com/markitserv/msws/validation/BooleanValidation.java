package com.markitserv.msws.validation;

import java.util.Map;

public class BooleanValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {

		if (target instanceof Boolean) {
			return ValidationResponse.createValidConvertedResponse(target);
		} else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			if("true".equalsIgnoreCase(targetStr) || "false".equalsIgnoreCase(targetStr)){
				return ValidationResponse.createValidConvertedResponse(new Boolean(targetStr));
			}else{
				return bad();
			}
		}
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected Boolean");
	}
}

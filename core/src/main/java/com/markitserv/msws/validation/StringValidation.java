package com.markitserv.msws.validation;

import java.util.Map;

/**
 * Ensures that a field is a string. It's essentially a NOOP, since all fields
 * come over the wire as a string
 * 
 * @author roy.truelove
 * 
 */
public class StringValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {

		if (target instanceof Integer) {
			return ValidationResponse.createValidConvertedResponse(target);
		} else {
			return bad();
		}
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected String");
	}

}

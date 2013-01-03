package com.markitserv.mwws.validation;

import java.util.Collection;
import java.util.Map;

public class CollectionValidation implements Validation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		if (target == null) {
			return ValidationResponse.createValidResponse();
		}

		if (!(target instanceof Collection<?>)) {
			return ValidationResponse
					.createInvalidResponse("Expected a collection");
		}

		return ValidationResponse.createValidResponse();
	}

}

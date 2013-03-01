package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

public class CollectionValidation extends AbstractOptionalValidation {

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {

		if (!(target instanceof Collection<?>)) {
			return ValidationAndConversionResponse
					.createInvalidResponse("Expected a collection");
		}

		return ValidationAndConversionResponse.createValidResponse();
	}
}

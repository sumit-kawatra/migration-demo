package com.markitserv.mwws.validation;

import java.util.Collection;
import java.util.Map;

public class CollectionValidation extends AbstractOptionalValidation {

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		if (!(target instanceof Collection<?>)) {
			return ValidationResponse
					.createInvalidResponse("Expected a collection");
		}

		return ValidationResponse.createValidResponse();
	}

}

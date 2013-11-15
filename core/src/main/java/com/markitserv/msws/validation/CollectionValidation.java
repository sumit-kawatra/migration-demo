package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

@Deprecated
public class CollectionValidation extends AbstractOptionalValidation {

	@Override
	@Deprecated
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {

		if (!(target instanceof Collection<?>)) {
			return ValidationResponse
					.createInvalidResponse("Expected a collection");
		}

		return ValidationResponse.createValidConvertedResponse(target);
	}
}

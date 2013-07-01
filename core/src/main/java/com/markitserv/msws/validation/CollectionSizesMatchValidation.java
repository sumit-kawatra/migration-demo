package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

/**
 * Ensures that two collections are the same size. For instance if SortBy has 3
 * elements, SortOrder must also have 3
 * 
 * @author roy.truelove
 * 
 */
public class CollectionSizesMatchValidation extends AbstractOptionalValidation {

	private String otherValueName;

	public CollectionSizesMatchValidation(String otherValueName) {
		super();
		this.otherValueName = otherValueName;
	}

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> otherValues) {

		Object otherTarget = otherValues.get(otherValueName);

		ValidationResponse isTargetACollection = new CollectionValidation()
				.validate(target, otherValues);
		ValidationResponse isOtherTargetACollection = new CollectionValidation()
				.validate(otherTarget, otherValues);

		if (!isTargetACollection.isValid()
				|| !isOtherTargetACollection.isValid()) {
			return ValidationResponse
					.createInvalidResponse(String
							.format("Expecting both this parameter and '%s' to be Collections", otherValueName));
		}

		Collection<?> targetCol = (Collection<?>) target;
		Collection<?> otherCol = (Collection<?>) otherTarget;

		if (targetCol.size() != otherCol.size()) {
			return ValidationResponse
					.createInvalidResponse(String
							.format("Expected this collection to have the same number "
									+ "of elements as the '%s' collection.  "
									+ "Instead '%s' has %d elements, expected %d.",
									otherValueName, otherValueName,
									otherCol.size(), targetCol.size()));
		}

		return ValidationResponse.createValidResponse();
	}
}

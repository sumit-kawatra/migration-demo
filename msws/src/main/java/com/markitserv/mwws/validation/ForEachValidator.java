package com.markitserv.mwws.validation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ForEachValidator extends AbstractOptionalValidation {

	private AbstractValidation forEachElement;

	public ForEachValidator(AbstractValidation forEachElement) {
		this.forEachElement = forEachElement;
	}

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		ValidationResponse isCollection = new CollectionValidation().isValid(
				target, map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;

		for (Object object : col) {
			ValidationResponse resp = forEachElement.isValid(object, map);
			if (!resp.isValid()) {
				return ValidationResponse.createInvalidResponse("Element '"
						+ object.toString()
						+ "' of this collection failed validation: "
						+ resp.getMessage());
			}
		}

		return ValidationResponse.createValidResponse();
	}
}

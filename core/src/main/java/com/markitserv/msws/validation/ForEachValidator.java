package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.markitserv.msws.internal.MswsAssert;

public class ForEachValidator extends AbstractOptionalValidation {

	private AbstractValidation forEachElement;

	public ForEachValidator(AbstractValidation forEachElement) {
		this.forEachElement = forEachElement;
	}

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {

		MswsAssert.mswsAssert(!(forEachElement instanceof AbstractConversionValidation),
				"You need to use the 'ForEachValidatorAndConverter' class for conversions");

		ValidationAndConversionResponse isCollection = new CollectionValidation().validate(
				target, map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;

		for (Object object : col) {
			ValidationAndConversionResponse resp = forEachElement.validate(object, map);
			if (!resp.isValid()) {
				return ValidationAndConversionResponse.createInvalidResponse("Element '"
						+ object.toString() + "' of this collection failed validation: "
						+ resp.getMessage());
			}
		}

		return ValidationAndConversionResponse.createValidResponse();
	}
}

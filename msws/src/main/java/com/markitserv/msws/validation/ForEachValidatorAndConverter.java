package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

public class ForEachValidatorAndConverter extends AbstractConversionValidation {

	private AbstractConversionValidation forEachElement;

	public ForEachValidatorAndConverter(AbstractConversionValidation forEachElement) {
		this.forEachElement = forEachElement;
	}

	@Override
	public ValidationAndConversionResponse validateAndConvert(Object target,
			Map<String, ? extends Object> map) {

		ValidationAndConversionResponse isCollection = new CollectionValidation().validate(
				target, map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;

		for (Object object : col) {
			ValidationAndConversionResponse resp = forEachElement
					.internalValidateAndConvert(object, map);
			if (!resp.isValid()) {
				return ValidationAndConversionResponse.createInvalidResponse("Element '"
						+ object.toString() + "' of this collection failed validation: "
						+ resp.getMessage());
			} else {
				target = resp.getConvertedObj();
			}
		}

		return ValidationAndConversionResponse.createValidConvertedResponse(col);
	}
}
package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

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
		Stack<Object> newCol = new Stack<Object>();

		for (Object object : col) {
			ValidationAndConversionResponse resp = forEachElement
					.internalValidateAndConvert(object, map);
			if (!resp.isValid()) {
				return ValidationAndConversionResponse.createInvalidResponse("Element '"
						+ object.toString() + "' of this collection failed validation: "
						+ resp.getMessage());
			} else {
				newCol.push(resp.getConvertedObj());
			}
		}

		return ValidationAndConversionResponse.createValidConvertedResponse(newCol);
	}
}
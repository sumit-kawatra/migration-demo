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
	public ValidationResponse validateAndConvert(Object target,
			Map<String, ? extends Object> map) {

		ValidationResponse isCollection = new CollectionValidation().validate(
				target, map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;
		Stack<Object> newCol = new Stack<Object>();

		for (Object object : col) {
			ValidationResponse resp = forEachElement
					.internalValidateAndConvert(object, map);
			if (!resp.isValid()) {
				return ValidationResponse.createInvalidResponse("Element '"
						+ object.toString() + "' of this collection failed validation: "
						+ resp.getMessage());
			} else {
				newCol.push(resp.getConvertedObj());
			}
		}

		return ValidationResponse.createValidConvertedResponse(newCol);
	}
}
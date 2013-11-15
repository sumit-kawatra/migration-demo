package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

public class ForEachValidator extends AbstractOptionalValidation {

	private AbstractValidation forEachElement;

	public ForEachValidator(AbstractValidation forEachElement) {
		this.forEachElement = forEachElement;
	}

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {

		ValidationResponse isCollection = new ListValidation().validate(target,
				map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;
		Stack<Object> newCol = new Stack<Object>();

		for (Object object : col) {
			ValidationResponse resp = forEachElement.validateInternal(object,
					map);
			if (!resp.isValid()) {
				return ValidationResponse.createInvalidResponse("Element '"
						+ object.toString()
						+ "' of this collection failed validation: "
						+ resp.getMessage());
			} else {
				newCol.push(resp.getConvertedObj());
			}
		}

		return ValidationResponse.createValidConvertedResponse(newCol);
	}
}
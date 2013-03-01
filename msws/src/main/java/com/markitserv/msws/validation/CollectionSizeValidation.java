package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

import com.markitserv.msws.exceptions.ProgrammaticException;

public class CollectionSizeValidation extends AbstractOptionalValidation {

	public static final int UNLIMITED = -1;

	private int max = UNLIMITED;
	private int min = UNLIMITED;

	public CollectionSizeValidation(int min, int max) {
		super();
		this.max = max;
		this.min = min;
	}

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {
		
		ValidationAndConversionResponse isCollection = new CollectionValidation().validate(target, map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;

		if (col.size() < min && min != UNLIMITED) {
			return ValidationAndConversionResponse.createInvalidResponse(String.format(
					"Expected a collection "
							+ "with a minimum of %d elements, but got a "
							+ "collection with %d elements.", min, col.size()));
		}

		if (col.size() > max && max != UNLIMITED) {
			return ValidationAndConversionResponse.createInvalidResponse(String.format(
					"Expected a collection "
							+ "with a maximum of %d elements, but got a "
							+ "collection with %d elements.", max, col.size()));
		}

		return ValidationAndConversionResponse.createValidResponse();
	}
}

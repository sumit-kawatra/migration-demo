package com.markitserv.mwws.validation;

import java.util.Collection;
import java.util.Map;

import com.markitserv.mwws.exceptions.ProgrammaticException;

public class CollectionSizeValidation implements Validation {

	public static final int UNLIMITED = -1;

	private int max = UNLIMITED;
	private int min = UNLIMITED;

	public CollectionSizeValidation(int min, int max) {
		super();
		this.max = max;
		this.min = min;
	}

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		if (target == null) {
			return ValidationResponse.createValidResponse();
		}
		
		ValidationResponse isCollection = new CollectionValidation().isValid(target, map);
		if (!isCollection.isValid()) {
			return isCollection;
		}

		Collection<?> col = (Collection<?>) target;

		if (col.size() < min && min != UNLIMITED) {
			return ValidationResponse.createInvalidResponse(String.format(
					"Expected a collection "
							+ "with a minimum of %d elements, but got a "
							+ "collection with %d elements.", min, col.size()));
		}

		if (col.size() > max && max != UNLIMITED) {
			return ValidationResponse.createInvalidResponse(String.format(
					"Expected a collection "
							+ "with a maximum of %d elements, but got a "
							+ "collection with %d elements.", min, col.size()));
		}

		return ValidationResponse.createValidResponse();
	}
}

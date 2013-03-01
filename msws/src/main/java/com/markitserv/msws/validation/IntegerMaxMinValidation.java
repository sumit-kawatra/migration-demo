package com.markitserv.msws.validation;

import java.util.Collection;
import java.util.Map;

import com.markitserv.msws.exceptions.ProgrammaticException;

public class IntegerMaxMinValidation extends AbstractConversionValidation {

	public static final int UNLIMITED = -1;

	private int max = UNLIMITED;
	private int min = UNLIMITED;

	public IntegerMaxMinValidation(int min, int max) {
		super();
		this.max = max;
		this.min = min;
	}

	@Override
	public ValidationAndConversionResponse validateAndConvert(Object target,
			Map<String, ? extends Object> map) {
		
		ValidationAndConversionResponse isInteger = new IntegerValidationAndConversion()
				.internalValidateAndConvert(target, map);
		if (!isInteger.isValid()) {
			return isInteger;
		}

		Integer i = (Integer) isInteger.getConvertedObj();

		if (i.compareTo(min) < 0 && min != UNLIMITED) {
			return ValidationAndConversionResponse.createInvalidResponse(String.format(
					"Expected a value greater than or equal to'%d' but got '%d'.", min, i));
		}

		if (i.compareTo(max) > 0 && max != UNLIMITED) {
			return ValidationAndConversionResponse.createInvalidResponse(String.format(
					"Expected a value less than or equal to '%d' but got '%d'.", max, i));
		}

		return ValidationAndConversionResponse.createValidConvertedResponse(i);
	}
}

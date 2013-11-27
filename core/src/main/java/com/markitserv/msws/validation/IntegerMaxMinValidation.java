package com.markitserv.msws.validation;

import java.util.Map;

public class IntegerMaxMinValidation extends AbstractOptionalValidation {

	public static final int UNLIMITED = -1;

	private int max = UNLIMITED;
	private int min = UNLIMITED;

	public IntegerMaxMinValidation(int min, int max) {
		super();
		this.max = max;
		this.min = min;
	}

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {

		ValidationResponse isInteger = new IntegerValidation()
				.validateInternal(target, map);
		if (!isInteger.isValid()) {
			return isInteger;
		}

		Integer i = (Integer) isInteger.getConvertedObj();

		if (i.compareTo(min) < 0 && min != UNLIMITED) {
			return ValidationResponse
					.createInvalidResponse(String
							.format("Expected a value greater than or equal to'%d' but got '%d'.",
									min, i));
		}

		if (i.compareTo(max) > 0 && max != UNLIMITED) {
			return ValidationResponse
					.createInvalidResponse(String
							.format("Expected a value less than or equal to '%d' but got '%d'.",
									max, i));
		}

		return ValidationResponse.createValidConvertedResponse(i);
	}

	@Override
	public String getDescription() {

		String top = (this.max == UNLIMITED) ? "(unlimited)" : "" + this.max;
		String bottom = (this.min == UNLIMITED) ? "(unlimited)" : "" + this.min;

		return "Integer between " + bottom + " and " + top + ", inclusive";
	}
}

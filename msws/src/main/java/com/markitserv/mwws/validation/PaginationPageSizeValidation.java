package com.markitserv.mwws.validation;

import java.util.Map;

import com.markitserv.mwws.Util.Constants;

public class PaginationPageSizeValidation extends IntegerMaxMinValidation {

	private int maxPageSize = Constants.INTEGER_NOT_SET;

	public PaginationPageSizeValidation(int maxPageSize) {
		super(1, maxPageSize);
		this.maxPageSize = maxPageSize;
	}

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		if (!super.isValid(target, map).isValid()) {
			return ValidationResponse.createInvalidResponse(String.format(
					"Page size cannot exceed '%d' for this action.",
					this.maxPageSize));
		}

		return ValidationResponse.createValidResponse();
	}
}

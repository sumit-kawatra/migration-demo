package com.markitserv.msws.validation;

import java.util.Map;

import com.markitserv.msws.internal.Constants;

public class PaginationPageSizeValidation extends IntegerMaxMinValidation {

	private int maxPageSize = Constants.INTEGER_NOT_SET;

	public PaginationPageSizeValidation(int maxPageSize) {
		super(1, maxPageSize);
		this.maxPageSize = maxPageSize;
	}

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {

		ValidationResponse resp = super.validate(target, map);
		
		if (!resp.isValid()) {
			return ValidationResponse.createInvalidResponse(String.format(
					"Page size cannot exceed '%d' for this action.",
					this.maxPageSize));
		}

		return resp;
	}
}

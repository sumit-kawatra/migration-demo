package com.markitserv.msws.validation;

import java.util.Map;

public class RequiredIfAnyProvidedValidation extends RequiredValidation {

	private String[] otherParams;

	public RequiredIfAnyProvidedValidation(String[] otherParams) {
		super();
		this.otherParams = otherParams;
	}

	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {

		boolean isTargetSet = super.validate(target, map).isValid();

		for (String other : otherParams) {
			Object otherVal = map.get(other);
			ValidationAndConversionResponse resp = super.validate(otherVal, map);
			if (resp.isValid() && !isTargetSet) {
				return ValidationAndConversionResponse
						.createInvalidResponse("Required when '" + other
								+ "'is provided.");
			}
		}

		return null;
	}

}

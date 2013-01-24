package com.markitserv.msws.validation;

import java.util.Map;

public class RequiredIfAnyProvidedValidation extends RequiredValidation {

	private String[] otherParams;

	public RequiredIfAnyProvidedValidation(String[] otherParams) {
		super();
		this.otherParams = otherParams;
	}

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		boolean isTargetSet = super.isValid(target, map).isValid();

		for (String other : otherParams) {
			Object otherVal = map.get(other);
			ValidationResponse resp = super.isValid(otherVal, map);
			if (resp.isValid() && !isTargetSet) {
				return ValidationResponse
						.createInvalidResponse("Required when '" + other
								+ "'is provided.");
			}
		}

		return null;
	}

}

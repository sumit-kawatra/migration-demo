package com.markitserv.msws.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.markitserv.msws.internal.MswsAssert;

public class OneOfValidation extends AbstractOptionalValidation {

	private String[] validValues;

	public OneOfValidation(String[] validValues) {
		super();
		init(validValues);
	}

	public OneOfValidation(List<String> validValues) {
		String[] values = validValues.toArray(new String[validValues.size()]);
		init(values);
	}

	private void init(String[] validValues) {
		Arrays.sort(validValues); // prereq to doing binary search later.
		this.validValues = validValues;
	}

	@Override
	public ValidationResponse isValid(Object target,
			Map<String, ? extends Object> map) {

		MswsAssert.mswsAssert(target instanceof String,
				"Expects target to be a string");

		MswsAssert.mswsAssert(validValues.length > 0,
				"Must specify at least one valid value for this validation");

		String targetStr = (String) target;
		boolean found = (Arrays.binarySearch(validValues, targetStr) >= 0);

		ValidationResponse resp;

		if (!found) {

			StringBuffer sb = new StringBuffer(
					"Expected value to be one of the following : [");
			for (String validValue : validValues) {
				sb.append(validValue);
				sb.append(",");
			}

			String msg = sb.toString();
			msg = StringUtils.chop(msg); // get rid of last comma

			resp = ValidationResponse.createInvalidResponse(String.format(
					"%s].  Instead got '%s'.", msg, targetStr));
		} else {
			resp = ValidationResponse.createValidResponse();
		}

		return resp;
	}

}

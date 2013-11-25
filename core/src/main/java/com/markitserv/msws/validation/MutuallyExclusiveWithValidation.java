/**
 * 
 */
package com.markitserv.msws.validation;

import java.util.Map;

/**
 * @author kiran.gogula
 * 
 */
public class MutuallyExclusiveWithValidation extends RequiredValidation {

	private String[] otherValues;

	public MutuallyExclusiveWithValidation(String[] otherValues) {
		this.otherValues = otherValues;
	}

	@Override
	public ValidationResponse validate(Object target,
			Map<String, ? extends Object> map) {
		int counter = 0;

		for (Object obj : otherValues) {
			if (isProvided( map.get(obj))) {
				counter++;
			}
		}
		if (isProvided(target) && counter == otherValues.length) {
			return ValidationResponse
					.createInvalidResponse(String
							.format("If this field is provided, the following fields are not applicable: "
									+ getStringFromArray(otherValues)));
		}else {
			return ValidationResponse.createValidConvertedResponse(target);
		}
	}

	private String getStringFromArray(String[] strArray) {
		StringBuffer sb = new StringBuffer();
		int count = 1;
		for (String string : strArray) {
			if (strArray.length > count) {
				sb.append(string).append(",");
			} else {
				sb.append(string);
			}
			count++;
		}
		return sb.toString();
	}

}

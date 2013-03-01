/**
 * 
 */
package com.markitserv.msws.validation;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author kiran.gogula
 *
 */
public class RequiredIfAllNotProvidedValidation extends RequiredValidation{

	private String[] otherValues;
	
	public RequiredIfAllNotProvidedValidation(String[] otherValues){
		this.otherValues = otherValues;
	}
	
	@Override
	public ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> map) {
		int counter = 0;
		
		for (Object obj : otherValues) {
			if (isProvided( map.get(obj))) {
				counter++;
			}
		}
		if(!isProvided(target) && counter == 0){
			return ValidationAndConversionResponse
					.createInvalidResponse("Required if any of the following are not provided:"
									+ getStringFromArray(otherValues));
		
		}
		return ValidationAndConversionResponse.createValidResponse();
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





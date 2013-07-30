package com.markitserv.msws.validation;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateValidation extends AbstractOptionalValidation {

	@Override
	protected ValidationResponse validate(Object target,
			Map<String, ? extends Object> otherValues) {
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		DateTime convertedDate = null;
		
		if (target instanceof DateTime)
			return ValidationResponse.createValidConvertedResponse(target);
		else if (!(target instanceof String)) {
			return bad();
		} else {
			String targetStr = (String) target;
			try {
				convertedDate = formatter.parseDateTime(targetStr);
			} catch (Exception e) {
				return bad();
			}
		}
		
		return ValidationResponse.createValidConvertedResponse(convertedDate);
	}

	private ValidationResponse bad() {
		return ValidationResponse.createInvalidResponse("Expected date in the form of 'YYYY-MM-DD'");
	}
}

package com.markitserv.msws.validation;

import java.util.Map;

import org.joda.time.DateTime;

import com.markitserv.msws.web.UploadedFile;

public class UploadFileValidation extends AbstractOptionalValidation {

	@Override
	protected ValidationResponse validate(Object target,
			Map<String, ? extends Object> otherValues) {
		
		if (target instanceof UploadedFile)
			return ValidationResponse.createValidConvertedResponse(target);
		else return ValidationResponse.createInvalidResponse("Expected a form-data file");
	}
}

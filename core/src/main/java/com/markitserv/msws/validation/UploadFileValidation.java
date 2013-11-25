package com.markitserv.msws.validation;

import java.util.Map;

import com.markitserv.msws.beans.UploadedFile;

public class UploadFileValidation extends AbstractOptionalValidation {

	@Override
	protected ValidationResponse validate(Object target,
			Map<String, ? extends Object> otherValues) {
		
		if (target instanceof UploadedFile)
			return ValidationResponse.createValidConvertedResponse(target);
		else return ValidationResponse.createInvalidResponse("Expected a form-data file");
	}
}

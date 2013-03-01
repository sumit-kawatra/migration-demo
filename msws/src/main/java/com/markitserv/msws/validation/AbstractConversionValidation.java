package com.markitserv.msws.validation;

import java.util.Map;

import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.MswsAssert;

public abstract class AbstractConversionValidation extends AbstractOptionalValidation {

	public ValidationAndConversionResponse internalValidateAndConvert(Object target,
			Map<String, ? extends Object> otherValues) {

		// because it's optional
		if (!isProvided(target)) {
			return ValidationAndConversionResponse.createValidResponse();
		}

		ValidationAndConversionResponse resp = this.validateAndConvert(target, otherValues);

		if (resp.isValid()) {
			MswsAssert.mswsAssert(resp.getConvertedObj() != null,
					"Expected a converted object, since this is a convertion validation.");
		}

		return resp;
	}

	protected abstract ValidationAndConversionResponse validateAndConvert(Object target,
			Map<String, ? extends Object> otherValues);

	@Override
	public final ValidationAndConversionResponse validateInternal(Object target,
			Map<String, ? extends Object> otherValues) {
		throw new ProgrammaticException(
				"Method not applicable for a conversion validator.  Use 'internalValidateAndConvert' instead.");
	}

	@Override
	protected final ValidationAndConversionResponse validate(Object target,
			Map<String, ? extends Object> otherValues) {
		throw new ProgrammaticException(
				"Method not applicable for a conversion validator.  Use 'internalValidateAndConvert' instead.");
	}
}

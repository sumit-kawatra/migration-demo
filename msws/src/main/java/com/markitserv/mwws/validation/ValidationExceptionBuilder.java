package com.markitserv.mwws.validation;

import java.util.List;
import java.util.Stack;

import org.springframework.stereotype.Service;

import com.markitserv.mwws.exceptions.ValidationException;

/**
 * Thrown when a given action has params or filters that don't pass validation.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class ValidationExceptionBuilder {

	public enum InvalidType {
		filter, param
	}

	private Stack<String> errorMsgs = new Stack<String>();

	private static final long serialVersionUID = 1L;

	/**
	 * @param type
	 * @param name
	 *            Name of the filter or the parameter that was passed in
	 */
	public ValidationExceptionBuilder() {
	}

	public ValidationExceptionBuilder addInvalidValidation(InvalidType type,
			ValidationResponse resp, String name) {
		String msg = String.format("Invalid %s '%s'.  %s", type.toString(),
				name, resp.getMessage());
		errorMsgs.push(msg);
		return this;
	}

	private ValidationException build() {
		StringBuilder sb = new StringBuilder();

		for (String msg : errorMsgs) {
			sb.append(msg);
		}

		return new ValidationException(sb.toString(), errorMsgs);
	}

	/**
	 * Throws the exception if there are any validation exceptions. If not, does
	 * nothing.
	 */
	public void buildAndThrowIfInvalid() {
		if (!errorMsgs.isEmpty()) {
			throw this.build();
		}
	}
}

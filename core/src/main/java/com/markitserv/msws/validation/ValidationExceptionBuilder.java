package com.markitserv.msws.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.springframework.stereotype.Service;

import com.markitserv.msws.exceptions.ValidationException;

/**
 * Thrown when a given action has params or filters that don't pass validation.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class ValidationExceptionBuilder {

	public enum FilterOrParam {
		filter, param
	}

	private Stack<String> errorMsgs = new Stack<String>();
	private Set<String> alreadyProcessed = new HashSet<String> ();

	private static final long serialVersionUID = 1L;

	/**
	 * @param type
	 * @param name
	 *            Name of the filter or the parameter that was passed in
	 */
	public ValidationExceptionBuilder() {
	}

	public ValidationExceptionBuilder addInvalidValidation(FilterOrParam type,
			ValidationResponse resp, String name) {
	
		// report only a single validation for each
		if (alreadyProcessed.contains(name)) {
			return this;
		}
		
		String msg = String.format("Invalid %s '%s'.  %s", type.toString(),
				name, resp.getMessage()+ "");
		errorMsgs.push(msg);
		alreadyProcessed.add(name);
		return this;
	}

	private ValidationException build() {
		StringBuilder sb = new StringBuilder();

		int counter = 1;
		for (String msg : errorMsgs) {
			sb.append("[Validation error # ");
			sb.append(counter);
			sb.append(".  ");
			sb.append(msg);
			sb.append("]  ");
			
			counter++;
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

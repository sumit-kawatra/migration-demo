package com.markitserv.msws.exceptions;


public class MultipleParameterValuesException extends MswsException {

	private static final long serialVersionUID = 1L;

	public MultipleParameterValuesException() {
	}

	public MultipleParameterValuesException(String message) {
		super(message);
	}

	public MultipleParameterValuesException(Throwable cause) {
		super(cause);
	}

	public MultipleParameterValuesException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultipleParameterValuesException(String format, Object... args) {
		super(format, args);
	}

	public MultipleParameterValuesException(String format, Throwable cause,
			Object... args) {
		super(format, cause, args);
	}

	public static MultipleParameterValuesException standardException(
			String paramName) {
		return new MultipleParameterValuesException(
				"Multiple values provided for parameter '%s'.  "
						+ "Only one value per parameter is supported",
				paramName);
	}
}
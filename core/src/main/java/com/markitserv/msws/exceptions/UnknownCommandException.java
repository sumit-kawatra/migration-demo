package com.markitserv.msws.exceptions;

import com.markitserv.msws.messaging.Command;

public class UnknownCommandException extends ProgrammaticException {

	private static final long serialVersionUID = 1L;

	private UnknownCommandException(String format, Object... args) {
		super(format, args);
	}

	public static UnknownCommandException standardException(Command cmd) {
		return new UnknownCommandException(
				"Cannot find command runner with for class '%s'", cmd.getClass()
						.getSimpleName());
	}
}

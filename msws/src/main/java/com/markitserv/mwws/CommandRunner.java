package com.markitserv.mwws;

/**
 * All CommandRunners should subclass this class
 * @author roy.truelove
 *
 */
public abstract class CommandRunner {

	/**
	 * Runs the command.  Async commands should return null.
	 * @param cmd The command to run
	 * @return The result of the command
	 */
	protected abstract Object run(Command cmd);
}

package com.markitserv.mwws.command;


/**
 * All CommandRunners should subclass this class
 * @author roy.truelove
 *
 */
public abstract class AbstractCommandRunner {

	/**
	 * Runs the command.  Async commands should return null.
	 * @param cmd The command to run
	 * @return The result of the command
	 */
	protected abstract Object run(Command cmd);
}

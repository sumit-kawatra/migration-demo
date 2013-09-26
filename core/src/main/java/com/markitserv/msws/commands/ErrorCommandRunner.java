/**
 * 
 */
package com.markitserv.msws.commands;

import static com.markitserv.msws.internal.MswsAssert.mswsAssert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.command.Command;
import com.markitserv.msws.command.internal.AbstractCommandRunner;
import com.markitserv.msws.command.internal.CommandDispatcher;
import com.markitserv.msws.exceptions.MswsException;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class ErrorCommandRunner extends AbstractCommandRunner {
	

	Logger log = LoggerFactory.getLogger(ErrorCommandRunner.class);

	private String env;

	private String errorDistributionGroup;

	@Autowired
	private CommandDispatcher dispatcher;

	@Override
	public Object run(Command cmd) throws MswsException {

		mswsAssert(cmd != null, "ErrorCommand must be set");

		ErrorCommand errorCmd = (ErrorCommand) cmd;

		// sendErrorEmail(cmd);
		log.error("Application threw an unexpected Programmatic Exception:",
				errorCmd.getError());

		return null; // since it's async
	}

	private void sendErrorEmail(Command cmd) {

		// TODO clean this up, need email template. Should include stacktrace.

		ErrorCommand errorCommand = (ErrorCommand) cmd;
		EmailCommand emailCommand = new EmailCommand();

		emailCommand.setSubject(getEnv() + "  " + errorCommand.getError());
		emailCommand.setBody(errorCommand.getError().getLocalizedMessage());
		emailCommand.setTo(getErrorDistributionGroup());

		// Sending the mail to error distribution group
		dispatcher.dispatchAsyncCommand(emailCommand);
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getErrorDistributionGroup() {
		return errorDistributionGroup;
	}

	public void setErrorDistributionGroup(String errorDistributionGroup) {
		this.errorDistributionGroup = errorDistributionGroup;
	}

	public CommandDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	@Override
	public Class<?> getCommandType() {
		return ErrorCommand.class;
	}
}

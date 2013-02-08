package com.markitserv.msws.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionCommandRunner;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;

@Service
public class CommandDispatcher {

	@Autowired
	public ActionCommandRunner actionCommandRunner;
	
	@Autowired
	public EmailCommandRunner emailCommandRunner;
	
	@Autowired
	public ErrorCommandRunner errorCommandRunner;

	public Object dispatchReqRespCommand(ReqRespCommand cmd) throws MswsException {
		// At some point this will be refactored to handle different types
		// of commands dynamically instead of having to know all of the runners
		// upfront
		if (cmd instanceof ActionCommand) {
			return actionCommandRunner.run(cmd);
		}else {		
			throw new ProgrammaticException("Don't yet know how to handle a "
					+ cmd.getClass().getSimpleName() + " command");
		}
	}

	/**
	 * <p></p>
	 * @param cmd
	 */
	public void dispatchAsyncCommand(AsyncCommand cmd) {
		if(cmd instanceof EmailCommand){
			emailCommandRunner.run(cmd);
		}if(cmd instanceof ErrorCommand){
			errorCommandRunner.run(cmd);
		}else{
			throw new ProgrammaticException("Don't yet know how to handle a "
					+ cmd.getClass().getSimpleName() + " command");
		}
	}

	public EmailCommandRunner getEmailCommandRunner() {
		return emailCommandRunner;
	}

	public void setEmailCommandRunner(EmailCommandRunner emailCommandRunner) {
		this.emailCommandRunner = emailCommandRunner;
	}

	public ErrorCommandRunner getErrorCommandRunner() {
		return errorCommandRunner;
	}

	public void setErrorCommandRunner(ErrorCommandRunner errorCommandRunner) {
		this.errorCommandRunner = errorCommandRunner;
	}
	
	
	
}

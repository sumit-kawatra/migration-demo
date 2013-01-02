package com.markitserv.mwws.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.mwws.action.ActionCommand;
import com.markitserv.mwws.action.ActionCommandRunner;
import com.markitserv.mwws.exceptions.ProgrammaticException;

@Service
public class CommandDispatcher {

	@Autowired
	public ActionCommandRunner actionCommandRunner;

	public Object dispatchReqRespCommand(ReqRespCommand cmd) {
		// At some point this will be refactored to handle different types
		// of commands dynamically instead of having to know all of the runners
		// upfront
		if (cmd instanceof ActionCommand) {
			return actionCommandRunner.run(cmd);
		} else {
			throw new ProgrammaticException("Don't yet know how to handle a "
					+ cmd.getClass().getSimpleName() + " command");
		}
	}

	public void dispatchAsyncCommand(AsyncCommand cmd) {
		throw new ProgrammaticException("Not implemented yet");
	}
}

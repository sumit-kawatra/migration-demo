package com.markitserv.msws.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.ActionCommandRunner;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.exceptions.ProgrammaticException;
import com.markitserv.msws.internal.MswsAssert;

@Service
public class CommandDispatcher {
	
	@Autowired
	private CommandRunnerRegistry registry;

	public Object dispatchReqRespCommand(ReqRespCommand cmd) throws MswsException {
		
		AbstractCommandRunner runner = registry.getCommandRunnerForCommand(cmd);
		return runner.run(cmd);
		
	}

	/**
	 * <p></p>
	 * @param cmd
	 */
	public void dispatchAsyncCommand(AsyncCommand cmd) {
		
		AbstractCommandRunner runner = registry.getCommandRunnerForCommand(cmd);
		Object retVal = runner.run(cmd);
		
		MswsAssert.mswsAssert(retVal == null, "AsyncCommand's 'run' method should always return null");
	}
} 
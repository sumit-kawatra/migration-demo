package com.markitserv.msws.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.command.AbstractCommandRunner;
import com.markitserv.msws.command.Command;
import com.markitserv.msws.command.ErrorCommand;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.internal.MswsAssert;

@Service
public class ActionCommandRunner extends AbstractCommandRunner {
	
	@Autowired
	private ActionRegistry registry;

	@Override
	public Object run(Command cmd) throws MswsException {
		
		ActionCommand aCmd = (ActionCommand)cmd;
		String actionName = aCmd.getAction();
		
		// will throw an exception if action is not found
		AbstractAction action = registry.getActionWithName(actionName);
		
		return action.performAction(aCmd);
		
	}
	
	@Override
	public Class<?> getCommandType() {
		return ActionCommand.class;
	}
}

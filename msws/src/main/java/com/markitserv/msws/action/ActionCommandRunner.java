package com.markitserv.msws.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.command.AbstractCommandRunner;
import com.markitserv.msws.command.Command;
import com.markitserv.msws.exceptions.MswsException;

@Service
public class ActionCommandRunner extends AbstractCommandRunner {
	
	@Autowired
	private ActionRegistry registry;

	@Override
	public Object run(Command cmd) throws MswsException {
		ActionCommand aCmd = (ActionCommand)cmd;
		String actionName = aCmd.getAction();
		AbstractAction action = registry.getActionWithName(actionName);
		return action.performAction(aCmd);
	}
}

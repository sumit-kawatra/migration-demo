package com.markitserv.mwws.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.mwws.command.Command;
import com.markitserv.mwws.command.CommandRunner;

@Service
public class ActionCommandRunner extends CommandRunner {
	
	@Autowired
	private ActionRegistry registry;

	@Override
	public Object run(Command cmd) {
		ActionCommand aCmd = (ActionCommand)cmd;
		String actionName = aCmd.getAction();
		Action action = registry.getActionWithName(actionName);
		return action.performAction(aCmd);
	}
}

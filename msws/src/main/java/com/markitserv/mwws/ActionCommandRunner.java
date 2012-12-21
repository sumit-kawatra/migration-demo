package com.markitserv.mwws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionCommandRunner extends CommandRunner {
	
	@Autowired
	private ActionRegistry registry;

	@Override
	protected Object run(Command cmd) {
		
		ActionCommand aCmd = (ActionCommand)cmd;
		String actionName = aCmd.getAction();
		Action action = registry.getActionWithName(actionName);
		return action.performAction(aCmd);
		
		
	}
}

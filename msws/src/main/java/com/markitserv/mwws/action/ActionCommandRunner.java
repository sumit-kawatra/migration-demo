package com.markitserv.mwws.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.mwws.command.Command;
import com.markitserv.mwws.command.AbstractCommandRunner;
import com.markitserv.mwws.exceptions.MwwsException;

@Service
public class ActionCommandRunner extends AbstractCommandRunner {
	
	@Autowired
	private ActionRegistry registry;

	@Override
	public Object run(Command cmd) throws MwwsException {
		ActionCommand aCmd = (ActionCommand)cmd;
		String actionName = aCmd.getAction();
		AbstractAction action = registry.getActionWithName(actionName);
		return action.performAction(aCmd);
	}
}

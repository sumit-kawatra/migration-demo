package com.markitserv.msws.action.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionRegistry;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.command.internal.AbstractCommandRunner;
import com.markitserv.msws.commands.ErrorCommand;
import com.markitserv.msws.exceptions.MswsException;
import com.markitserv.msws.internal.MswsAssert;
import com.markitserv.msws.messaging.Command;

@Service
public class ActionDispatcher {
	
	@Autowired
	private ActionRegistry registry;

	public ActionResult dispatch(ActionCommand aCmd) {
		
		String actionName = aCmd.getAction();
		
		// will throw an exception if action is not found
		AbstractAction action = registry.getActionWithName(actionName);
		
		return action.internalPerformAction(aCmd);
		
	}
}

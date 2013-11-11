package com.markitserv.msws.actions;

import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.internal.ActionCommand;

@Service
public class DescribeSession extends AbstractAction {

	@Override
	protected ActionResult performAction(ActionCommand command) {
		
		return new ActionResult(command.getSessionInfo());
	}

}

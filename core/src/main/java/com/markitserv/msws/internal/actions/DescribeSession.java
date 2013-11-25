package com.markitserv.msws.internal.actions;

import org.springframework.stereotype.Service;

import com.markitserv.msws.action.AbstractAction;
import com.markitserv.msws.action.ActionCommand;
import com.markitserv.msws.action.resp.ActionResult;

@Service
public class DescribeSession extends AbstractAction {

	@Override
	protected ActionResult performAction(ActionCommand command) {

		return new ActionResult(command.getRequestContext().getSession());
	}

	@Override
	public String getDescription() {
		return "Describes the session of the currently logged in user.";
	}

}
